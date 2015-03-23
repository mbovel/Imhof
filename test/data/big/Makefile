main: lausanne.osm lausanne.osm.gz berne.osm berne.osm.gz interlaken.osm interlaken.osm.gz

lausanne.osm.gz:
	curl -o $@ http://cs108.epfl.ch/files/lausanne.osm.gz

lausanne.osm: lausanne.osm.gz
	gunzip -c lausanne.osm.gz > $@

berne.osm.gz:
	curl -o $@ http://cs108.epfl.ch/files/berne.osm.gz

berne.osm: berne.osm.gz
	gunzip -c berne.osm.gz > $@

interlaken.osm.gz:
	curl -o $@ http://cs108.epfl.ch/files/interlaken.osm.gz

interlaken.osm: interlaken.osm.gz
	gunzip -c interlaken.osm.gz > $@