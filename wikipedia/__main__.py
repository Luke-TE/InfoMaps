from sanic import Sanic
from sanic.response import json
import requests
from wiki_interface import get_results

app = Sanic()


@app.route("/", methods=["POST",])
async def test(request):
    params = request.json
    print(f"json: {request.json}")
    if 'countries' in params.keys():
        countries = params['countries']
    else:
        countries = []
    query = params['query'] if 'query' in params.keys() else ""
    print(f"query: {query}")
    results = get_results(countries, query)


    # print(results)
    # print(results.items())
    # output = {'results': results}
    return json(results)


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=8080)
