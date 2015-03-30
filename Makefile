all:
	$(MAKE) -C test/data/big
	$(MAKE) -C test_official

clean:
	$(MAKE) -C test/data/big clean
	$(MAKE) -C test_official clean