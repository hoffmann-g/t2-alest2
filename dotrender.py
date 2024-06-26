import os
import pydot

dot_filename = 'output.dot'
png_filename = 'output.png'

if os.path.exists(dot_filename):
    os.remove(dot_filename)
if os.path.exists(png_filename):
    os.remove(png_filename)

(graph,) = pydot.graph_from_dot_file(dot_filename)
graph.write_png(png_filename)

print("Graph visual representation exported.")
