# WWW_Visualizer
A basic unoptimized web crawler written in Java, supports visualization via [Graphia](https://graphia.app/)

![WWW Visualizer FAZ depth 9](https://user-images.githubusercontent.com/49553574/221014837-f061fafb-7a5e-4b66-bc79-ff7b30aa3229.png)
root site: https://www.faz.net/aktuell/ \
depth: 9

## Example usage:
```
java -jar .\WWW.Visualizer.Crawler-1.0.jar
Enter mode (crawl, append):
> crawl

Enter root site: https://www.nytimes.com/
Enter maximum depth: 4
...
```
I recommend using some sort of newspaper website as root site because these usually contain a lot of links.

Convert to GML file:
```
python .\GML.Converter.py
```

Now you can open `graph.gml` via Graphia.

## Append files
By using the `append` mode you can add the data of file `B.json` to file `A.json` (the root site of B has to be inside A)
