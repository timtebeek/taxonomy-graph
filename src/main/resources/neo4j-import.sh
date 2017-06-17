set -e;

## Store all data in home
DATADIR="$HOME/neo4j/data/"
mkdir -p $DATADIR;
cd $DATADIR;


## Download & extract
#curl -o taxdump.tar.gz ftp://ftp.ncbi.nih.gov/pub/taxonomy/taxdump.tar.gz;
tar -xf taxdump.tar.gz;


## Transform input files
# Prefix names with a unique numerical ID (not available inside docker image)
nl -s '	|	' names.dmp > names_prefixed.dmp;

# Fix problematic lines in citations.dmp
#36396	|	The Plant List - Polygala	|	0	|	0	|	Gymnadenia rhellicani	Nigritella rhellicani	|		|	4275 1384028 1384029 	|
#sed -i -e 's/[^|]	[^|]/ /g' citations.dmp
#7698	|	James G & Gilbert GL (unpublished_1998)	|	0	|	0	|		|	James, G., and Gilbert, G.L. \"Mycobacterium sp. nov. 16S ribosomal RNA.\" Unpublished (as of 28 December 1998)	|86002 	|
#sed -i 's/|86002  |/|	86002	|/g' citations.dmp


# Write out header files
# division.dmp: Division entities
echo 'division_id:ID(Division)	|	code	|	name	|	comments	|' > division.psv;
# gencode.dmp: Gencode entities
echo 'gencode_id:ID(Gencode)	|	abbreviation	|	name	|	cde	|	starts	|' > gencode.psv;
# nodes.dmp: Taxon entities
echo 'tax_id:ID(Taxon)	|	parent_tax_id	|	rank	|	embl_code	|	division_id:IGNORE	|	inherited_div_flag	|	genetic_code_id:IGNORE	|	inherited_gc_flag	|	mitochondrial_genetic_code_id:IGNORE	|	inherited_mgc_flag	|	genbank_hidden_flag	|	hidden_subtree_root_flag	|	comments	|' > nodes.psv;
# names.dmp: Name entities
echo 'name_id:ID(Name)	|	tax_id:IGNORE	|	name	|	unique_name	|	name_class	|' > names.psv;
# citations.dmp: Name entities
echo 'cit_id:ID(Citation)	|	cit_key	|	pubmed_id:int	|	medline_id:int	|	url:IGNORE	|	text	|	tax_id_list:IGNORE	|' > citations.psv;

# nodes.dmp: Relationship HAS_PARENT
echo 'tax_id:START_ID(Taxon)	|	parent_tax_id:END_ID(Taxon)	|	rank:IGNORE	|	embl_code:IGNORE	|	division_id:IGNORE	|	inherited_div_flag:IGNORE	|	genetic_code_id:IGNORE	|	inherited_gc_flag:IGNORE	|	mitochondrial_genetic_code_id:IGNORE	|	inherited_mgc_flag:IGNORE	|	genBank_hidden_flag:IGNORE	|	hidden_subtree_root_flag:IGNORE	|	comments:IGNORE	|' > nodes.parent.psv;
# nodes.dmp: Relationship HAS_DIVISION
echo 'tax_id:START_ID(Taxon)	|	parent_tax_id:IGNORE	|	rank:IGNORE	|	embl_code:IGNORE	|	division_id:END_ID(Division)	|	inherited_div_flag:IGNORE	|	genetic_code_id:IGNORE	|	inherited_gc_flag:IGNORE	|	mitochondrial_genetic_code_id:IGNORE	|	inherited_mgc_flag:IGNORE	|	genBank_hidden_flag:IGNORE	|	hidden_subtree_root_flag:IGNORE	|	comments:IGNORE	|' > nodes.division.psv;
# nodes.dmp: Relationship HAS_GENCODE
echo 'tax_id:START_ID(Taxon)	|	parent_tax_id:IGNORE	|	rank:IGNORE	|	embl_code:IGNORE	|	division_id:IGNORE	|	inherited_div_flag:IGNORE	|	genetic_code_id:END_ID(Gencode)	|	inherited_gc_flag:IGNORE	|	mitochondrial_genetic_code_id:IGNORE	|	inherited_mgc_flag:IGNORE	|	genBank_hidden_flag:IGNORE	|	hidden_subtree_root_flag:IGNORE	|	comments:IGNORE	|' > nodes.gencode.psv;
# nodes.dmp: Relationship HAS_MITGENCODE
echo 'tax_id:START_ID(Taxon)	|	parent_tax_id:IGNORE	|	rank:IGNORE	|	embl_code:IGNORE	|	division_id:IGNORE	|	inherited_div_flag:IGNORE	|	genetic_code_id:IGNORE	|	inherited_gc_flag:IGNORE	|	mitochondrial_genetic_code_id:END_ID(Gencode)	|	inherited_mgc_flag:IGNORE	|	genBank_hidden_flag:IGNORE	|	hidden_subtree_root_flag:IGNORE	|	comments:IGNORE	|' > nodes.mitgencode.psv;

# names.dmp: Relationship HAS_NAME
echo 'name_id:END_ID(Name)	|	tax_id:START_ID(Taxon)	|	name:IGNORE	|	unique_name:IGNORE	|	name_class:IGNORE	|' > names.taxon.psv;

# citations.dmp: Relationship HAS_CITATION
echo 'cit_id:END_ID(Citation)	|	cit_key:IGNORE	|	pubmed_id:IGNORE	|	medline_id:IGNORE	|	url:IGNORE	|	text:IGNORE	|	tax_id_list:START_ID[](Taxon)	|' > citations.taxon.psv;


## Write import script to datadir
IMPORT="set -e;
rm -rf data/databases/graph.db;
./bin/neo4j-import \\
	--into data/databases/graph.db \\
	--delimiter '|' \\
	--array-delimiter ' ' \\
	--quote '	' \\
	--trim-strings \\
	--id-type integer \\
	--nodes:Division data/division.psv,data/division.dmp \\
	--nodes:Gencode data/gencode.psv,data/gencode.dmp \\
	--nodes:Taxon data/nodes.psv,data/nodes.dmp \\
	--nodes:Name data/names.psv,data/names_prefixed.dmp \\
	--relationships:HAS_PARENT data/nodes.parent.psv,data/nodes.dmp \\
	--relationships:HAS_DIVISION data/nodes.division.psv,data/nodes.dmp \\
	--relationships:HAS_GENCODE data/nodes.gencode.psv,data/nodes.dmp \\
	--relationships:HAS_MITGENCODE data/nodes.mitgencode.psv,data/nodes.dmp \\
	--relationships:HAS_NAME data/names.taxon.psv,data/names_prefixed.dmp \\
#	--nodes:Citation data/citations.psv,data/citations.dmp \\
#	--relationships:HAS_CITATION data/citations.taxon.psv,data/citations.dmp
" > "$DATADIR/import.sh";
echo "$IMPORT" > "$DATADIR/import.sh";
chmod +x "$DATADIR/import.sh";

echo 'Done';
