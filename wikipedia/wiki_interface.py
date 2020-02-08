import wikipedia
from bs4 import BeautifulSoup

COUNTRY_ALIASES = ["country", "countries", "nation", "nations"]


def get_results(countries, query):
    query = "countries by " + query
    print(f"full query:{query}")
    countries = list(map(lambda x: x.lower(), countries))
    page = wikipedia.page(query)
    print(page.title)
    html = page.html()
    b = BeautifulSoup(html, 'html.parser')

    tables = b.find_all('table')
    table_index, country_col_index, col_names, table = get_first_table(tables)
    if table_index is None:
        return {}

    num_cols = get_num_table_cols(table)

    country_to_data = dict()
    table_rows = table.find_all('tr')
    for table_row in table_rows:
        table_cells = table_row.find_all('td')
        if len(table_cells) == 0:
            continue

        country = table_cells[country_col_index].string

        if country is None:
            link = table_cells[country_col_index].find('a')
            country = link['title']
        else:
            country = str(country)

        country = country.rstrip()

        if len(countries) == 0 or country.lower() in countries:
            field_dict = dict()
            for index, table_cell in enumerate(table_cells):
                if index == country_col_index:
                    field_dict[col_names[country_col_index]] = country
                else:
                    if table_cell.string is None:
                        if table.caption is None:
                            field_dict[col_names[index]] = "?"
                        else:
                            field_dict[col_names[index]] = table.caption
                        field_dict[col_names[index]] = table.caption
                    else:
                        field_dict[col_names[index]] = table_cell.string.rstrip()

            country_to_data[country] = field_dict

    return country_to_data

def get_num_table_cols(table):
    return len(table.find('tr').find_all('th'))


def get_first_table(tables, backup_value="?"):
    for table_index, table in enumerate(tables):
        table_first_row = table.find('tr')
        col_headers = table_first_row.find_all('th')

        col_names = list()
        for col_header in col_headers:
            if col_header.text is None:
                if table.caption is None:
                    col_names.append(backup_value)
                else:
                    col_names.append(table.caption)
            else:
                col_names.append(col_header.text)

        for col_index, col_name in enumerate(col_names):
            for country_alias in COUNTRY_ALIASES:
                if country_alias in col_name.lower():
                    return table_index, col_index, col_names, table
    return None, None, None, None
