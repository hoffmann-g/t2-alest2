import pydot

(graph,) = pydot.graph_from_dot_file('output.dot')
graph.write_png('output.png')

print("graph visual representation exported")