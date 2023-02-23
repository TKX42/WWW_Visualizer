import json
import networkx as nx

net = nx.Graph()

def parseSite(site):
    net.add_node(site["url"])
    
    for link in site["links"]:
        parseSite(link)
        net.add_edge(site["url"], link["url"])



def main():
    data = json.load(open("./root.json", encoding="utf8"))
    parseSite(data)

    nx.write_gml(net, "./graph.gml")

    print("done.")

if(__name__ == "__main__"):
    main()