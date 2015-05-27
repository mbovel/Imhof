file = imhof_tests.zip
subfolders = data/OSMtestFiles test/ch

all: $(subfolders)

$(subfolders): $(file)
	unzip -n $(file) "$@/*"

$(file):
	curl -o $@ http://cs108.epfl.ch/files/$@

clean:
	rm -rf $(subfolders)