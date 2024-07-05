import os
import pydot

dot_filename = 'output.dot'
png_filename = 'output.png'

(graph,) = pydot.graph_from_dot_file(dot_filename)
graph.set_graph_defaults(rankdir='TB')

if os.path.exists(png_filename):
    os.remove(png_filename)

graph.write_png(png_filename)

print("Graph visual representation exported.")
