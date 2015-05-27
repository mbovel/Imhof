gz_files = lausanne.osm.gz berne.osm.gz interlaken.osm.gz
osm_files = lausanne.osm berne.osm interlaken.osm
final_images = lausanne.png berne.png interlaken.png
final_images_zip = Imhof_maps.zip
dem_files = N46E006.hgt N46E007.hgt
dem_zip = imhof-dems.zip
images = shaded-orientation.png lausanne_150dpi.png interlaken_72dpi.png lausanne_bb.png
files_path = http://cs108.epfl.ch/files/
images_path = http://cs108.epfl.ch/images/

all: $(osm_files) $(gz_files) $(final_images) $(images) $(dem_files)

$(gz_files) $(dem_zip) $(final_images_zip):
	curl -o $@ $(files_path)$@
	
$(images):
	curl -o $@ $(images_path)$@

$(osm_files): $(gz_files)
	gunzip -c $@.gz > $@

$(dem_files): $(dem_zip)
	unzip -n $(dem_zip) $@

$(final_images): $(final_images_zip)
	unzip -n $(final_images_zip) $@

clean:
	rm -rf $(osm_files)
	rm -rf $(gz_files)
	rm -rf $(final_images)
	rm -rf $(final_images_zip)
	rm -rf $(images)