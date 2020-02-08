import wikipedia
from bs4 import BeautifulSoup

COUNTRY_ALIASES = ["country", "countries"]


def get_results(countries, query):
    countries = list(map(lambda x: x.lower(), countries))
    html = wikipedia.page(query).html()
    b = BeautifulSoup(html, 'html')
    tables = b.find_all('table')
    table_index, country_col_index, table = get_first_table(tables)
    if table_index is None:
        return {}

    num_cols = get_num_table_cols(table)
    data_col_index = country_col_index - 1 if country_col_index == num_cols - 1 else country_col_index + 1

    country_to_data = dict()
    table_rows = table.find_all('tr')
    for table_row in table_rows:
        table_cells = table_row.find_all('td')
        if len(table_cells) == 0:
            continue

        for i in table_cells:
            print(i)

        country = table_cells[country_col_index].string

        if country is None:
            link = table_cells[country_col_index].find('a')
            country = link['title'].rstrip()
        else:
            country = str(country).rstrip()

        if country.lower() in countries:
            data = str(table_cells[data_col_index].string).rstrip()
            country_to_data[country] = data

    return country_to_data

def get_num_table_cols(table):
    return len(table.find('tr').find_all('th'))

def get_first_table(tables):
    for table_index, table in enumerate(tables):
        table_first_row = table.find('tr')
        col_names = table_first_row.find_all('th')
        print(col_names)
        for col_index, col_name in enumerate(col_names):
            for country_alias in COUNTRY_ALIASES:
                if country_alias in str(col_name.string).lower():
                    return table_index, col_index, table
    return None, None, None
