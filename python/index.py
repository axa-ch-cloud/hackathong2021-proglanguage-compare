from flask import Flask
import flask
import uuid
import json
from draw import draw

app = Flask(__name__)


@app.route('/mandelbrot',methods = ['POST'])
def mandelbrotCreate():
    json_data = flask.request.json
    a_height = json_data["height"]
    a_width = json_data["width"]
    uuidString = str(uuid.uuid4())
    result = draw(a_width, a_height)
    f = open("db." + uuidString + ".json", "a")
    f.write(json.dumps(result))
    f.close()
    return uuidString

@app.route('/mandelbrot/<uuid>')
def getMandelbrot(uuid):
    f = open("db." + uuid + ".json" , "r")
    data = f.read()
    response = app.response_class(
        response=data,
        status=200,
        mimetype='application/json'
    )
    return response


@app.route("/")
def index():
    return "<h1>Hello World!</h1>"

if __name__ == "__main__":
    from waitress import serve
    serve(app, host="0.0.0.0", port=8080, threads=24)
