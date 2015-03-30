gz_files = lausanne.osm.gz berne.osm.gz interlaken.osm.gz
osm_files = lausanne.osm berne.osm interlaken.osm

all: $(osm_files) $(gz_files)

$(gz_files):
	curl -o $@ http://cs108.epfl.ch/files/$@

$(osm_files): $(gz_files)
	gunzip -c $@.gz > $@

clean:
	rm -rf $(osm_files)
	rm -rf $(gz_files)