from sanic import Sanic
from sanic.response import json

from wiki_interface import get_results

app = Sanic()


@app.route("/")
async def test(request):
    request = "countries by GDP"
    countries = ["United Kingdom"]
    results = get_results(countries, request)
    return json(results)


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=8000)
