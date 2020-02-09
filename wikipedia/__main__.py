from sanic import Sanic
from sanic.response import json
from wiki_interface import get_results

app = Sanic()


@app.route("/", methods=["POST",])
async def test(request):
    countries = []
    query = str(request.form['query'])
    print(f"query: {query}")
    results = get_results(countries, query)

    # print(results)
    # print(results.items())
    # output = {'results': results}
    return json(results)


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=8080)
