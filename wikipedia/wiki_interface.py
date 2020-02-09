import wikipedia
import requests
from bs4 import BeautifulSoup

COUNTRY_ALIASES = ["country", "countries", "nation", "nations"]

def get_results(countries, query):
    query = "countries by " + query
    print(f"full query:{query}")
    countries = list(map(lambda x: x.lower(), countries))
    try:
        page = wikipedia.page(query)
    except wikipedia.PageError:
        return {}
    
    print(page.title)
    html = page.html()
    b = BeautifulSoup(html, 'html.parser')

    tables = b.find_all('table')
    table_index, country_col_index, col_names, table = get_first_table(tables)
    if table_index is None:
        return {}

    num_table_cols = get_num_table_cols(table)

    if country_col_index == num_table_cols - 1:
        val_index = country_col_index - 1
    else:
        val_index = country_col_index + 1

    col_names[val_index] = "value"

    country_to_data = list()
    table_rows = table.find_all('tr')
    for table_row in table_rows:
        table_cells = table_row.find_all('td')
        if len(table_cells) == 0:
            continue

        country = table_cells[country_col_index].string

        if country is None:
            link = table_cells[country_col_index].find('a')
            if not (link is None) and link.get('title', '') != '':
                country = link['title']
            else:
                country = "?"
        else:
            country = str(country)

        country = country.rstrip()

        if len(countries) == 0 or country.lower() in countries:
            field_dict = dict()

            URL = f"https://restcountries.eu/rest/v2/name/{country}"
            r = requests.get(url=URL)
            try:
                data = r.json()[0]
            except:
                continue

            # print(data['alpha2Code'])
            field_dict['id'] = data['alpha2Code']

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

            country_to_data.append(field_dict)

    return country_to_data


def get_num_table_cols(table):
    return len(table.find('tr').find_all('th'))


def get_first_table(tables, backup_value="?"):
    for table_index, table in enumerate(tables):
        table_rows = table.find_all('tr')

        col_headers_list = list()
        for table_row in table_rows:
            col_headers = table_row.find_all('th')
            if col_headers:
                col_headers_list.append(col_headers)
            else:
                break

        num_header_rows = len(col_headers_list)
        col_names_list = list()

        for hr in range(num_header_rows):
            global_index = 0
            local_index = 0
            while local_index < len(col_headers_list[hr]):
                while global_index < len(col_names_list) and len(col_names_list[global_index]) > hr:
                    global_index += 1

                col_header = col_headers_list[hr][local_index]
                col_width = int(col_header.get('colspan', 1))
                row_width = int(col_header.get('rowspan', 1))

                new_col_part = []
                for r in range(row_width):
                    parsed_col_header = parse_wiki_elem(col_header.text)
                    if parsed_col_header == "":
                        if table.caption is None:
                            new_col_part.append(backup_value)
                        else:
                            new_col_part.append(parse_wiki_elem(table.caption.text))
                    else:
                        new_col_part.append(parsed_col_header)

                for c in range(col_width):
                    add_if_possible(new_col_part[:], global_index + c, col_names_list)

                global_index += col_width
                local_index += 1

        col_names = []
        for col_list in col_names_list:
            combined = []
            for col in col_list:
                if col not in combined:
                    combined.append(col)

            col_names.append(" ".join(combined))

        print(col_names)
        for col_index, col_name in enumerate(col_names):
            for country_alias in COUNTRY_ALIASES:
                if country_alias in col_name.lower():
                    return table_index, col_index, col_names, table
    return None, None, None, None


def add_if_possible(list_of_items, index, list_of_lists_of_items):
    if len(list_of_lists_of_items) == index:
        list_of_lists_of_items.append(list_of_items)
    else:
        list_of_lists_of_items[index] += list_of_items

def parse_wiki_elem(elem):
    parsed = str(elem).rstrip()
    first_ref_index = parsed.find("[")
    if first_ref_index >= 0:
        return parsed[:first_ref_index]
    return parsed
