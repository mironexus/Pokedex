# Pokedex

## Structure of project:

database - Files needed for Room functionality; application uses only one table in a database, a table to store favorites ids and timestamps. Timestamps are used for sort and permanent reordering of Pokemons which is implemented by switching Pokemon timestamps

## 

mainactivityfragments - Fragments that main activity uses and they all use same viewmodel
	- favorites
		- Gets its data by first fetching all ids from database and then uses those ids with network call that uses id as a parameter and also enables reordering by switching timestamps
	- search
		- Loads 20 by 20 Pokemons and shows them by implementing Paging Library; also, PokemonListDataSource is the only class that isn't viewmodel that uses repository 
	- settings
		- Fragment that enables language change (not yet implemented) and access to AboutActivity
		
 ## 
network - Files for Retrofit functionality, most of network calls use full links instead of Query and Path variables because lot of API responses already had full links in them

## 
otheractivities:
## 
pokemon
	- Uses dedicated viewmodel for fetching single Pokemon with the network call that uses id as a parameter that is sent to Activity from recyclerview item of Main Activity's PokemonSearchFragment or FavoritePokemonsFragment; has SingleStat custom view for presentation of single Pokemon stat
##
type
	- PokemonTypeActivity is a tabbed activity with three fragments and all of them use same viewmodel to fetch their respective data. First fetching is implemented in activity itself, Type is fetched by using url that is sent from PokemonActivity

## 
repository - Dedicated for handling network calls and storing data to database
