//Or Cohen 307852681
//Jonas Zerbib 340941301
package database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

public class DatabaseFoundations {

	// Creating our database
	static {
		Connection conn = null;
		ResultSet rs = null;
		DatabaseMetaData meta = null;
		String createDb = "CREATE DATABASE IF NOT EXISTS GameWarior";
		try {
			// Create our database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/", "root", "");
			Statement statement = (Statement) conn.createStatement();
			statement.executeUpdate(createDb);
			String dbUrl = "jdbc:mysql://localhost/gameWarior";
			conn = DriverManager.getConnection(dbUrl, "root", "");
			meta = conn.getMetaData();
			// Checks if our tables exist in the data base if not create them
			try {
			rs = meta.getTables(null, null, "Users", null);
			if (!rs.next()) {
				System.out.println("Creating database");
				System.out.println("Creating table-Users");
				createTableUsers(conn);
				createUsersDb(conn);
			}
			rs = meta.getTables(null, null, "Games", null);
			if (!rs.next()) {
				System.out.println("Creating table-Games");
				createTableGames(conn);
				createGamesDb(conn);
			}
			rs = meta.getTables(null, null, "Orders", null);
			if (!rs.next()) {
				System.out.println("Creating table-Orders");
				createTableOrders(conn);
				createOrdersDb(conn);
			}
			rs = meta.getTables(null, null, "OrderLines", null);
			if (!rs.next()) {
				System.out.println("Creating table-OrderLines");
				createTableOrderLines(conn);
				createOrderLinesDb(conn);
			}
			rs = meta.getTables(null, null, "Genre", null);
			if (!rs.next()) {
				System.out.println("Creating table-Genre");
				createTableGenre(conn);
				createGenreDb(conn);
			}

			rs = meta.getTables(null, null, "Rating", null);
			if (!rs.next()) {
				System.out.println("Creating table-Rating");
				createTableRating(conn);
				createRatingDb(conn);
			}
			System.out.println("Connected to database");
			}
			catch(Exception createError)
			{
				System.out.println(createError.getMessage());
				System.out.println(createError.getStackTrace());
				Statement statementTwo= (Statement) conn.createStatement();
				System.out.println("deleting database");
				statementTwo.executeUpdate("DROP DATABASE GameWarior;");
			}

		} catch (Exception error) {
			System.out.println("Error in connecting the database");
			System.out.println(error.getMessage());
			System.out.println(error.getStackTrace());
		}
	}

	// Functions to create our database tables
	// Create user table
	public static void createTableUsers(Connection conn) throws Exception {
		Statement statement = (Statement) conn.createStatement();
		String table = "CREATE TABLE  Users (" + "id int NOT NULL AUTO_INCREMENT," + "userName text NOT NULL,"
				+ "email text NOT NULL," + "password text NOT NULL," + "firstName text NOT NULL,"
				+ "lastName text NOT NULL," + "birthday text NOT NULL," + "phone text NOT NULL,"
				+ "gender text NOT NULL," + "type text NOT NULL," + "PRIMARY KEY(id));";
		statement.executeUpdate(table);
		return;
	}

	// Create game table
	public static void createTableGames(Connection conn) throws Exception {
		Statement statement = (Statement) conn.createStatement();
		String table = "CREATE TABLE Games (" + "id int NOT NULL AUTO_INCREMENT," + "price double NOT NULL,"
				+ "name text NOT NULL," + "summary text NOT NULL," + "developer text NOT NULL,"
				+ "publisher text NOT NULL," + "trailer text NOT NULL," + "picture text NOT NULL,"
				+ "releaseDate text NOT NULL," + "relevant text NOT NULL  DEFAULT 'Yes'," + "PRIMARY KEY(id));";
		statement.executeUpdate(table);
		return;
	}

	// Create order table
	public static void createTableOrders(Connection conn) throws Exception {
		Statement statement = (Statement) conn.createStatement();
		String table = "CREATE TABLE Orders (" + "numOrder int NOT NULL AUTO_INCREMENT," + "idClient int NOT NULL,"
				+ "PRIMARY KEY(numOrder)); ";
		statement.executeUpdate(table);
		return;
	}

	// Create Order lines table
	public static void createTableOrderLines(Connection conn) throws Exception {
		Statement statement = (Statement) conn.createStatement();
		String table = "Create TABLE OrderLines(" + "numOrder int NOT NULL," + "gameId int NOT NULL);";
		statement.executeUpdate(table);
		return;
	}

	// Create Genre table
	public static void createTableGenre(Connection conn) throws Exception {
		Statement statement = (Statement) conn.createStatement();
		String table = "Create TABLE Genre(" + "genre text NOT NULL," + "gameId int NOT NULL);";
		statement.executeUpdate(table);
		return;
	}

	// Create Rating table
	public static void createTableRating(Connection conn) throws Exception {
		Statement statement = (Statement) conn.createStatement();
		String table = "Create TABLE Rating(" + "idClient int NOT NULL," + "gameId int NOT NULL,"
				+ "rating double NOT NULL DEFAULT '0');";
		statement.executeUpdate(table);
		return;
	}

	// Insert values to Users table
	public static void createUsersDb(Connection conn) throws Exception {
		Statement statement = (Statement) conn.createStatement();
		String insertToTable = "INSERT INTO Users(id,userName,email,password,firstName,lastName,birthday,phone,gender,type) VALUES"
				+ "(1,'Admin','admin@gmail.com','123456','Or','Cohen','1994-12-06','0564432132','Male','Admin'),"
				+ "(2,'Admin1','admin1@gmail.com','123456','Jonas','Zerbib','1992-10-07','0504674332','Male','Admin'),"
				+ "(3,'User1','User1@gmail.com','1234567','Mika','Smith','1997-02-11','0523499921','Female','Client'),"
				+ "(4,'User2','User2@gmail.com','1234','Ron','Feldman','1989-04-15','0507232412','Male','Client'),"
				+ "(5,'User3','User3@gmail.com','12343','Nick','Diaz','1979-09-11','054753412','Male','Client'),"
				+ "(6,'User4','User4@gmail.com','12344','John','Felx','1999-02-01','0507844254','Male','Client'),"
				+ "(7,'User5','User5@gmail.com','12345','Yael','Israel','1993-01-02','0525454876','Female','Client'),"
				+ "(8,'User6','User6@gmail.com','12346','Dana','Lavi','1989-04-15','0536556701','Female','Client'),"
				+ "(9,'User7','User7@gmail.com','12347','Roni','Cohen','1995-12-01','0532454782','Female','Client'),"
				+ "(10,'User8','User8@gmail.com','12348','Toni','Smith','1994-07-23','0523416653','Male','Client'),"
				+ "(11,'User9','User9@gmail.com','12349','Mor','Hoger','1975-11-20','0584543565','Male','Client'),"
				+ "(12,'User10','User10@gmail.com','123410','Ricky','Segerim','1979-01-12','0503489872','Male','Client'),"
				+ "(13,'User11','User11@gmail.com','123411','John','Faco','1997-01-19','0556652452','Male','Client'),"
				+ "(14,'User12','User12@gmail.com','123412','Jenny','Silin','1974-06-12','0509324441','Female','Client'),"
				+ "(15,'User13','User13@gmail.com','123413','Micki','Fox','1967-07-17','0506654412','Male','Client'),"
				+ "(16,'User14','User14@gmail.com','123414','Sia','Conem','1981-01-01','0532323445','Female','Client'),"
				+ "(17,'User15','User15@gmail.com','123415','Dina','Gopel','1991-02-01','053211266','Female','Client'),"
				+ "(18,'User16','User16@gmail.com','123416','Tobi','Smoke','1972-09-22','0523411188','Male','Client'),"
				+ "(19,'User17','User17@gmail.com','123417','Mordi','Honis','1985-11-22','0582772165','Male','Client'),"
				+ "(20,'User18','User18@gmail.com','123418','Rick','Seger','1979-03-19','0503453572','Male','Client'),"
				+ "(21,'User19','User19@gmail.com','123419','Donny','Franco','1987-07-17','0501123452','Male','Client'),"
				+ "(22,'User20','User20@gmail.com','123420','Hila','Felds','1984-05-25','0507764312','Female','Client'),"
				+ "(23,'User21','User21@gmail.com','123421','Lenny','Bren','1959-08-22','0508877452','Male','Client'),"
				+ "(24,'User22','User22@gmail.com','123422','Kenny','Rnae','1988-08-22','0505885712','Male','Client'),"
				+ "(25,'User23','User23@gmail.com','123425','Lisa','Mans','1994-05-13','0527125632','Female','Client')"
				+ ";";
		statement.executeUpdate(insertToTable);

	}

	// Insert values to Games table
	public static void createGamesDb(Connection conn) throws Exception {
		Statement statement = (Statement) conn.createStatement();
		String insertToTable = "INSERT INTO Games(id,price,name,summary,developer,publisher,trailer,picture,releaseDate,relevant) VALUES"
				+ "(1,'276','STAR WARS Jedi: Fallen Order','A galaxy-spanning adventure awaits in Star Wars Jedi: Fallen Order, a new third-person action-adventure title from Respawn Entertainment. This narratively driven, single-player game puts you in the role of a Jedi Padawan who narrowly escaped the purge of Order 66 following the events of Episode 3: Revenge of the Sith. On a quest to rebuild the Jedi Order, you must pick up the pieces of your shattered past to complete your training, develop new powerful Force abilities and master the art of the iconic lightsaber - all while staying one step ahead of the Empire and its deadly Inquisitors.\r\n"
				+ "\r\n"
				+ "While mastering your abilities, players will engage in cinematically charged lightsaber and Force combat designed to deliver the kind of intense Star Wars lightsaber battles as seen in the films. Players will need to approach enemies strategically, sizing up strengths and weaknesses while cleverly utilizing your Jedi training to overcome your opponents and solve the mysteries that lay in your path.\r\n"
				+ "\r\n"
				+ "Star Wars fans will recognize iconic locations, weapons, gear and enemies while also meeting a roster of fresh characters, locations, creatures, droids and adversaries new to Star Wars. As part of this authentic Star Wars story, fans will delve into a galaxy recently seized by the Empire. As a Jedi hero-turned-fugitive, players will need to fight for survival while exploring the mysteries of a long-extinct civilization all in an effort to rebuild the remnants of the Jedi Order as the Empire seeks to erase the Jedi completely.\r\n"
				+ "\r\n"
				+ "','Respawn Entertainment','Electronic Arts','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/0GLbwkfhYZk\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>','https://technoinfoplus.com/wp-content/uploads/2020/02/maxresdefault-17-640x360.jpg','2019-11-15','Yes')"
				+ ",(2,'230','Red Dead Redemption 2','Red Dead Redemption 2, the critically acclaimed open world epic from Rockstar Games and the highest rated game of the console generation, now enhanced for PC with new Story Mode content, visual upgrades and more.\r\n"
				+ "\r\n"
				+ "Red Dead Redemption 2, the critically acclaimed open world epic from Rockstar Games and the highest rated game of the console generation, now enhanced for PC with new Story Mode content, visual upgrades and more.\r\n"
				+ "\r\n"
				+ "America, 1899. The end of the wild west era has begun. After a robbery goes badly wrong in the western town of Blackwater, Arthur Morgan and the Van der Linde gang are forced to flee. With federal agents and the best bounty hunters in the nation massing on their heels, the gang must rob, steal and fight their way across the rugged heartland of America in order to survive. As deepening internal divisions threaten to tear the gang apart, Arthur must make a choice between his own ideals and loyalty to the gang who raised him.\r\n"
				+ "\r\n"
				+ "With all new graphical and technical enhancements for deeper immersion, Red Dead Redemption 2 for PC takes full advantage of the power of the PC to bring every corner of this massive, rich and detailed world to life including increased draw distances; higher quality global illumination and ambient occlusion for improved day and night lighting; improved reflections and deeper, higher resolution shadows at all distances; tessellated tree textures and improved grass and fur textures for added realism in every plant and animal.\r\n"
				+ "\r\n"
				+ "Red Dead Redemption 2 for PC also offers HDR support, the ability to run high-end display setups with 4K resolution and beyond, multi-monitor configurations, widescreen configurations, faster frame rates and more.\r\n"
				+ "\r\n"
				+ "Red Dead Redemption 2 for PC also includes additional Story Mode content including Bounty Hunting Missions, Gang Hideouts, Weapons and more; plus free access to the shared living world of Red Dead Online featuring all previously released improvements and the latest content for the complete Online experience including Frontier Pursuits and the specialist Roles of The Bounty Hunter, Trader and Collector, and much more.\r\n"
				+ "\r\n"
				+ "Red Dead Redemption 2 for PC is the ultimate way to experience one of the most critically acclaimed games of all time, winner of over 175 Game of the Year Awards and recipient of over 250 perfect scores.','Rockstar Games','Rockstar Games','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/gmA6MrX81z4\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>','https://files.geektime.co.il/wp-content/uploads/2018/11/rdr2-officialart-3840x2160-1541066749.jpg','2019-12-05','Yes')"
				+ ",(3,'249','DRAGON BALL Z: KAKAROT','BE THE HOPE OF THE UNIVERSE\r\n" + "\r\n"
				+ "• Experience the story of DRAGON BALL Z from epic events to light-hearted side quests, including never-before-seen story moments that answer some burning questions of DRAGON BALL lore for the first time!\r\n"
				+ "\r\n"
				+ "• Play through iconic DRAGON BALL Z battles on a scale unlike any other. Fight across vast battlefields with destructible environments and experience epic boss battles against the most iconic foes (Raditz, Frieza, Cell etc…). Increase your power level through RPG mechanics and rise to the challenge!\r\n"
				+ "\r\n"
				+ "• Don’t just fight as Z Fighters. Live like them! Fish, fly, eat, train, and battle your way through the DRAGON BALL Z sagas, making friends and building relationships with a massive cast of DRAGON BALL characters.\r\n"
				+ "\r\n"
				+ "Relive the story of Goku and other Z Fighters in DRAGON BALL Z: KAKAROT! Beyond the epic battles, experience life in the DRAGON BALL Z world as you fight, fish, eat, and train with Goku, Gohan, Vegeta and others. Explore the new areas and adventures as you advance through the story and form powerful bonds with other heroes from the DRAGON BALL Z universe.'"
				+ ",'CyberConnect2 Co. Ltd.','BANDAI NAMCO Entertainment','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/RoAG2g8oXQw\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>','https://cdn-products.eneba.com/resized-products/4Niqxs9XlkCg2qaip3EZZo2ntYIfZjEfoQRc2itdFi0_390x400_1x-0.jpeg','2020-01-17','Yes')"
				+ ",(4,'85','The Elder Scrolls Online','Experience an ever-expanding story across all of Tamriel in The Elder Scrolls Online, an award-winning online RPG. Explore a rich, living world with friends or embark upon a solo adventure. Enjoy complete control over how your character looks and plays, from the weapons you wield to the skills you learn – the choices you make will shape your destiny. Welcome to a world without limits.\r\n"
				+ "PLAY THE WAY YOU LIKE\r\n"
				+ "Battle, craft, steal, siege, or explore, and combine different types of armor, weapons, and abilities to create your own style of play. The choice is yours to make in a persistent, ever-growing Elder Scrolls world.\r\n"
				+ "TELL YOUR OWN STORY\r\n"
				+ "Discover the secrets of Tamriel as you set off to regain your lost soul and save the world from Oblivion. Experience any story in any part of the world, in whichever order you choose – with others or alone.\r\n"
				+ "A MULTIPLAYER RPG\r\n"
				+ "Complete quests with friends, join fellow adventurers to explore dangerous, monster-filled dungeons, or take part in epic PvP battles with hundreds of other players.',"
				+ "'Zenimax Online Studios','Bethesda Softworks','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/uq1o2VG1ogY\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>','https://steamcdn-a.akamaihd.net/steam/apps/306130/header.jpg?t=1579799208','2014-04-04','Yes')"
				+ ",(5,'66.55','Tank Mechanic Simulator','Tank Mechanic Simulator is a game about World War II tanks, their crews and their contribution in military history.\r\n"
				+ "\r\n" + "\r\n" + "\r\n"
				+ "As a tank museum owner your task is to recover destroyed or abandoned tanks and renovate them.\r\n"
				+ "\r\n" + "\r\n" + "\r\n"
				+ "Use your offroad vehicle to locate forgotten tanks. With final area found, determine the exact position of the tank by using special equipment like metal detector. Set up excavation area and extract the tank!\r\n"
				+ "\r\n" + "\r\n" + "\r\n"
				+ "Transport freshly extracted tanks to your warehouse. Then start working on renovation process. Detach rusted elements and replace them with new parts. Buy them or fix them by yourself!\r\n"
				+ "\r\n" + "\r\n" + "\r\n"
				+ "Tank Mechanic Simulator contains physical terrain deformation system, player will have to mind where he wants to travel and excavate.\r\n"
				+ "\r\n" + "\r\n" + "\r\n" + "Perform the entire renovation program starting from:\r\n"
				+ "cleaning the tank from mud\r\n" + "removing rust\r\n" + "sand blasting the tank\r\n"
				+ "placing a primer coat\r\n" + "painting tank with final paint\r\n" + "\r\n" + "\r\n" + "\r\n"
				+ "You can excavate even when the night comes! The game extraction missions have full night and day cycle implemented.',"
				+ "'DeGenerals','PlayWay S.A.','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/3SA5_m56quI\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>','https://steamcdn-a.akamaihd.net/steam/apps/407130/header.jpg?t=1582409899','2020-02-20','Yes')"
				+ ",(6,'18.47','Besiege','Besiege is a physics based building game in which you construct medieval siege engines and lay waste to immense fortresses and peaceful hamlets. Build a machine which can crush windmills, wipe out battalions of brave soldiers and transport valuable resources, defending your creation against cannons, archers and whatever else the desperate enemies have at their disposal. Create a trundling behemoth, or take clumsily to the skies, and cause carnage in fully destructible environments. Ultimately, you must conquer every Kingdom by crippling their castles and annihilating their men and livestock, in as creative or clinical a manner as possible!\r\n"
				+ "\r\n" + "\r\n" + "\r\n"
				+ "Battle against other players in custom arenas or team up with your friends to destroy armies of knights, obliterate fortresses and sack settlements, by travelling through into the Multiverse!\r\n"
				+ "Download other player’s war machines, levels, skins and even mods through the Steam workshop to further customize your playing experience!\r\n"
				+ "\r\n"
				+ "Can’t find what you are looking for? Build it yourself! In the level editor, you decide where players spawn, what the objective is and what it looks like. Adding sheep and peasants will bring a level of charm to your game but archers or yak-bombs will challenge your players as they attempt to complete the objective.\r\n"
				+ "\r\n"
				+ "Still looking for more? Expand the Besiege experience by creating your own mods or downloading existing mods from the workshop. Mods allow you to add custom machine blocks, level objects, level logic and much more!\r\n"
				+ "\r\n" + "Bring your childhood fantasies alive in the world of Besiege!',"
				+ "'Spiderling Studios','Spiderling Studios','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/n-aG7E9gTNI\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>','https://hb.imgix.net/f8bdda0c7992e14689ea77f4aa479d9733d1da51.png?auto=compress,format&fit=crop&h=353&w=616&s=d00b2766475282b74526e5f926ba55d8','2020-02-18','Yes')"
				+ ",(7,'73.95','Rocket League','Rocket League is a high-powered hybrid of arcade-style soccer and vehicular mayhem with easy-to-understand controls and fluid, physics-driven competition. Rocket League includes casual and competitive Online Matches, a fully-featured offline Season Mode, special Mutators that let you change the rules entirely, hockey and basketball-inspired Extra Modes, and more than 500 trillion possible cosmetic customization combinations.\r\n"
				+ "\r\n"
				+ "Winner or nominee of more than 150 Game of the Year awards, Rocket League is one of the most critically-acclaimed sports games of all time. Boasting a community of more than 57 million players, Rocket League features ongoing free and paid updates, including new DLCs, content packs, features, modes and arenas.',"
				+ "'Psyonix LLC','Psyonix LLC','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/Vawwy2eu5sA\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>','https://i2.wp.com/www.ubergizmo.com/wp-content/uploads/2019/01/rocket-league.jpg?fit=1200%2C675&ssl=1','2015-06-07','Yes')"
				+ ",(8,'224.95','NBA 2K20','WELCOME TO THE NEXT\r\n"
				+ "NBA 2K has evolved into much more than a basketball simulation. 2K continues to redefine what’s possible in sports gaming with NBA 2K20, featuring best in class graphics & gameplay, ground breaking game modes, and unparalleled player control and customization. Plus, with its immersive open-world Neighborhood, NBA 2K20 is a platform for gamers and ballers to come together and create what’s next in basketball culture.\r\n"
				+ "\r\n" + "BEST IN CLASS GAMEPLAY\r\n"
				+ "Take your skills to the next level with the most realistic player control ever, featuring an upgraded motion engine with signature styles, advanced shooting controls, a new dribble size-up system, refined off-ball collisions, and a new read & react defensive game.\r\n"
				+ "\r\n" + "MyCAREER\r\n"
				+ "Up-and-coming visionary Sheldon Candis directs the most visually stunning MyCAREER cinematic experience to date. A star-studded cast including Idris Elba, Rosario Dawson, and NBA all-stars past and present bring the journey to life in a completely new and immersive way.\r\n"
				+ "\r\n" + "THE NEXT NEIGHBORHOOD\r\n"
				+ "Experience a more vibrant, active Neighborhood. Access even more 2K Compete Events, unlock animations with the new Show-Off Stick, play a round on the 9 hole Disc Golf course, and earn more exclusive gear than ever before.\r\n"
				+ "\r\n" + "BRING YOUR GAME & REP UP\r\n"
				+ "The Park remains the center stage where players hone their skills and battle to be the best. And with the return of Park Rep, everyone will know who’s legit and who needs a seat on the bench. Unlock exclusive items as you rep up, and use them on any of your MyPLAYER builds! Tons of new prizes available with the new and improved Rep system.\r\n"
				+ "\r\n" + "MyPLAYER BUILDER\r\n"
				+ "More control than ever before. The new MyPLAYER Builder allows you to make decisions on every aspect of your MyPLAYER’s potential, including choosing your own Takeover. With over 100 archetypes and 50 new badges, the combinations are nearly endless.\r\n"
				+ "\r\n" + "WELCOME TO THE WNBA\r\n"
				+ "For the first time, all 12 WNBA teams and over 140 players are in the game and ready to run in Play Now and Season modes. Complete with gameplay animations, play styles, and visuals built exclusively for the women’s game.\r\n"
				+ "\r\n" + "MyTEAM\r\n"
				+ "NBA 2K’s fantasy card collector. Master MyTEAM with daily goals, card-leveling, a reimagined Triple Threat, limited-time events, and even more prizes. Enjoy a simplified user experience that both veterans and rookies will appreciate, and stay connected to the community with Locker Codes, Leaderboards, Developer Tips, Team of the Week, and more.\r\n"
				+ "\r\n" + "DYNAMIC SOUNDTRACK\r\n"
				+ "In collaboration with Steve Stoute and United Masters, this year’s soundtrack features a diverse array of top songs from both well-known and up-and-coming artists from across the globe.\r\n"
				+ "\r\n" + "LEGENDARY TEAMS\r\n"
				+ "Play with over 10 new legendary teams from the past, including the 2009-10 Portland Trail Blazers, 2015-16 Cleveland Cavaliers, 2013-14 San Antonio Spurs, 2002-03 Phoenix Suns, and All-Decade teams from every era of NBA history. Over 100 total legendary teams to choose from.\r\n"
				+ "\r\n" + "NEXT LEVEL PRESENTATION\r\n"
				+ "Dynamic, broadcast-quality gameplay presentation featuring the deepest roster of talent in sports videogame history, led by Kevin Harlan, Ernie Johnson, and many more. It’s an audio experience unlike any other, with over 60,000 new lines of dialogue, all-new studio shows and game intros, MyPLAYER interviews, records and milestone coverage, and over 2,000 arena specific crowd reactions and sounds.\r\n"
				+ "\r\n" + "MyGM/MyLEAGUE\r\n"
				+ "Think you can build the next dynasty? Take full control of a franchise and develop a champion from the ground up. Featuring new skill trees, a revamped relationship system, simulator customization, revised scouting, and more.\r\n"
				+ "\r\n" + "2KTV – SEASON 6\r\n"
				+ "Hosted by Alexis Morgan & Chris Manning, NBA 2KTV returns for another season as the hub for all things NBA 2K. Featuring members of the 2K community, exclusive interviews with NBA & WNBA stars, the latest 2K20 news, tips & insights directly from developers, and your weekly Top Plays!',"
				+ "'Visual Concepts','2K','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/FQ7WBnSvjIo\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>','https://quizizz.com/media/resource/gs/quizizz-media/quizzes/83763848-8a01-4fd1-9820-e1d758add040?w=200&h=200','2019-09-05','Yes')"
				+ ",(9,'75.00','Age of Empires II: Definitive Edition','Age of Empires II: Definitive Edition celebrates the 20th anniversary of one of the most popular strategy games ever with stunning 4K Ultra HD graphics, a new and fully remastered soundtrack, and brand-new content, “The Last Khans” with 3 new campaigns and 4 new civilizations.\r\n"
				+ "\r\n"
				+ "Explore all the original campaigns like never before as well as the best-selling expansions, spanning over 200 hours of gameplay and 1,000 years of human history. Head online to challenge other players with 35 different civilizations in your quest for world domination throughout the ages.\r\n"
				+ "\r\n"
				+ "Choose your path to greatness with this definitive remaster to one of the most beloved strategy games of all time.',"
				+ "' Forgotten Empires, Tantalus Media, Wicked Witch','Xbox Game Studios','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/N6kd1SYHW5k\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>','https://steamcdn-a.akamaihd.net/steam/apps/813780/capsule_616x353.jpg?t=1573159743','2019-11-14','Yes')"
				+ ",(10,'139.95','The Witcher 3: Wild Hunt','The Witcher: Wild Hunt is a story-driven open world RPG set in a visually stunning fantasy universe full of meaningful choices and impactful consequences. In The Witcher, you play as professional monster hunter Geralt of Rivia tasked with finding a child of prophecy in a vast open world rich with merchant cities, pirate islands, dangerous mountain passes, and forgotten caverns to explore.',"
				+ "'CD PROJEKT RED','CD PROJEKT RED','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/ehjJ614QfeM\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>','https://cdn-cf.gamivo.com/image_cover.jpg?f=39894&n=16592430106019007.jpg&h=551129fd6b7e467c0620423e16ac5017','2015-05-18','Yes'),"
				+ "(11,'89','Assassin''s Creed Origins - Gold Edition','ASSASSIN''S CREED ORIGINS IS A NEW BEGINNING\r\n"
				+ "\r\n"
				+ "*The Discovery Tour by Assassin''s Creed: Ancient Egypt is available now as a free update!*\r\n"
				+ "\r\n"
				+ "Ancient Egypt, a land of majesty and intrigue, is disappearing in a ruthless fight for power. Unveil dark secrets and forgotten myths as you go back to the one founding moment: The Origins of the Assassin''s Brotherhood.\r\n"
				+ "\r\n" + "A COUNTRY TO DISCOVER\r\n"
				+ "Sail down the Nile, uncover the mysteries of the pyramids or fight your way against dangerous ancient factions and wild beasts as you explore this gigantic and unpredictable land.\r\n"
				+ "\r\n" + "A NEW STORY EVERY TIME YOU PLAY\r\n"
				+ "Engage into multiple quests and gripping stories as you cross paths with strong and memorable characters, from the wealthiest high-born to the most desperate outcasts.\r\n"
				+ "\r\n" + "EMBRACE ACTION-RPG\r\n"
				+ "Experience a completely new way to fight. Loot and use dozens of weapons with different characteristics and rarities. Explore deep progression mechanics and challenge your skills against unique and powerful bosses.','Ubisoft Montreal','Ubisoft','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/cK4iAjzAoas\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>','https://images-na.ssl-images-amazon.com/images/I/91oDYnmXyML.jpg','2017-10-27','Yes'),"
				+ "(12,'65.57','Tom Clancy''s The Division Gold Edition','During Black Friday, a devastating pandemic sweeps through New York City, and one by one, basic services fail. In only a few days, without food or water, society collapses into chaos. The Division, an autonomous unit of tactical agents, is activated. Leading seemingly ordinary lives among us, these Agents are trained to operate independently in order to save society.\r\n"
				+ "\r\n" + "When Society Falls, We Rise.\r\n"
				+ "','Massive Entertainment','Ubisoft','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/sli7AbX2bEk\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>','https://upload.wikimedia.org/wikipedia/en/a/af/The_Division_2_art.jpg','2016-03-08','Yes'),"
				+ "(13,'119.70','Watch Dogs2 Gold Edition','Play as Marcus Holloway, a brilliant young hacker living in the birthplace of the tech revolution, the San Francisco Bay Area.\r\n"
				+ "Team up with Dedsec, a notorious group of hackers, to execute the biggest hack in history; take down ctOS 2.0, an invasive operating system being used by criminal masterminds to monitor and manipulate citizens on a massive scale.\r\n"
				+ "\r\n" + "Explore the dynamic open-world, full of gameplay possibilities\r\n"
				+ "Hack into every connected device and take control of the city infrastructure.\r\n"
				+ "Develop different skills to suit your playstyle, and upgrade your hacker tools – RC cars, Quadcopter drone, 3D printed weapons and much more.\r\n"
				+ "Stay seamlessly connected to your friends with a brand new co-op and adversarial multiplayer Watch Dogs experience.\r\n"
				+ "\r\n" + "PUT YOUR EYES IN CTRL\r\n"
				+ "Get the upper hand with Tobii Eye Tracking. Let your gaze aid you in weaponizing the “internet of things”, aim at enemies and take cover while you naturally explore the environment. Combine the extensive eye tracking feature set to pinpoint enemies, interact with your surroundings, locate shelter points, and rapidly select hackable targets. Let your vision lead the hacking of the city’s digital brain.\r\n"
				+ "\r\n" + "Compatible with all Tobii Eye Tracking gaming devices.\r\n" + "----\r\n"
				+ "Additional notes:\r\n"
				+ "Eye tracking features available with Tobii Eye Tracking.','Ubisoft','Ubisoft','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/WAiTIM6GFLk\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>','https://upload.wikimedia.org/wikipedia/en/thumb/b/b0/Watch_Dogs_2.jpg/220px-Watch_Dogs_2.jpg','2016-11-29','Yes'),"
				+ "(14,'87.60','Far Cry New Dawn','Dive into a transformed vibrant post-apocalyptic Hope County, Montana, 17 years after a global nuclear catastrophe.\r\n"
				+ "Join fellow survivors and lead the fight against the dangerous new threat the Highwaymen, and their ruthless leaders The Twins, as they seek to take over the last remaining resources.\r\n"
				+ "\r\n" + "\r\n" + "FIGHT FOR SURVIVAL IN A POST-APOCALYPTIC WORLD\r\n"
				+ "• Take up arms on your own or with a friend in two player co-op in an unpredictable and transformed world\r\n"
				+ "\r\n" + "\r\n" + "COLLIDE AGAINST TWO ALL NEW VILLAINS\r\n"
				+ "• Recruit an eclectic cast of Guns and Fangs for Hire and form alliances to fight by your side against the Highwaymen''s unruly leaders The Twins\r\n"
				+ "\r\n" + "\r\n" + "BUILD UP YOUR HOMEBASE AND THE SURVIVORS\r\n"
				+ "• Recruit Specialists to upgrade your homebase, who will help unlock all new features including crafting weapons, gear and more\r\n"
				+ "\r\n" + "\r\n" + "BATTLE FOR RESOURCES IN HOPE COUNTY AND BEYOND\r\n"
				+ "• Engage the Highwaymen in Turf Wars and venture on Expeditions to memorable locations across the USA\r\n"
				+ "', 'Ubisoft Montreal, Ubisoft Kiev, Ubisoft Shanghai','Ubisoft','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/HQHrjM7aCe0\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>','https://cdn-products.eneba.com/resized-products/VBRbgdNXc146sWyIztXGEd3B2K38hoKWDj8565uiUKI_390x400_1x-0.jpeg','2019-02-15','Yes')"
				+ ",(15,'55.46','Commandos 2 - HD Remaster','Relive the real-time tactics masterpiece that defined the genre like no other: Originally developed by the legendary Pyro Studios, Commandos 2 - HD Remaster is a true homage to one of gaming’s most celebrated masterpieces. Experience Commandos 2 Men of Courage like never before in high definition with reworked controls, UI and tutorial.\r\n"
				+ "\r\n" + "\r\n" + "\r\n"
				+ "Take control of an elite group of commandos who must venture deep into enemy territory and utilize their combined expertise to complete a series of notoriously demanding missions set in World War II. Go covert into various environments based on authentic World War II locations and lead your team of commandos against overwhelming odds, operate covertly and turn the tide of war.\r\n"
				+ "','Yippee! Entertainment, Pyro Studios','Kalypso Media','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/tb90nBvuwl4\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>','https://i.ytimg.com/vi/noBRYZGa7_o/maxresdefault.jpg','2020-01-24','Yes'),"
				+ "(16,'224.95','Sid Meiers Civilization VI','Originally created by legendary game designer Sid Meier, Civilization is a turn-based strategy game in which you attempt to build an empire to stand the test of time. Become Ruler of the World by establishing and leading a civilization from the Stone Age to the Information Age. Wage war, conduct diplomacy, advance your culture, and go head-to-head with history’s greatest leaders as you attempt to build the greatest civilization the world has ever known.\r\n"
				+ "\r\n"
				+ "Civilization VI offers new ways to engage with your world: cities now physically expand across the map, active research in technology and culture unlocks new potential, and competing leaders will pursue their own agendas based on their historical traits as you race for one of five ways to achieve victory in the game.\r\n"
				+ "\r\n" + "EXPANSIVE EMPIRES:\r\n"
				+ "See the marvels of your empire spread across the map like never before. Each city spans multiple tiles so you can custom build your cities to take full advantage of the local terrain.\r\n"
				+ "ACTIVE RESEARCH:\r\n"
				+ "Unlock boosts that speed your civilization’s progress through history. To advance more quickly, use your units to actively explore, develop your environment, and discover new cultures.\r\n"
				+ "DYNAMIC DIPLOMACY:\r\n"
				+ "Interactions with other civilizations change over the course of the game, from primitive first interactions where conflict is a fact of life, to late game alliances and negotiations.\r\n"
				+ "COMBINED ARMS:\r\n"
				+ "Expanding on the “one unit per tile” design, support units can now be embedded with other units, like anti-tank support with infantry, or a warrior with settlers. Similar units can also be combined to form powerful “Corps” units.\r\n"
				+ "ENHANCED MULTIPLAYER:\r\n"
				+ "In addition to traditional multiplayer modes, cooperate and compete with your friends in a wide variety of situations all designed to be easily completed in a single session.\r\n"
				+ "A CIV FOR ALL PLAYERS:\r\n"
				+ "Civilization VI provides veteran players new ways to build and tune their civilization for the greatest chance of success. New tutorial systems introduce new players to the underlying concepts so they can easily get started.','Firaxis Games, Aspyr (Mac), Aspyr (Linux)','2K, Aspyr (Mac), Aspyr (Linux)','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/5KdE0p2joJw\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>','https://downloads.2kgames.com/vnbh3gjdfkeit4utey6pdmds12/civ/img/CivilizationVI_keyart_horizontal_thm.jpg','2016-10-21','Yes'),"
				+ "(17,'135.96','Planet Zoo','Build a world for wildlife in Planet Zoo. From the developers of Planet Coaster and Zoo Tycoon comes the ultimate zoo sim, featuring authentic living animals who think, feel and explore the world you create around them. Experience a globe-trotting campaign or let your imagination run wild in the freedom of Sandbox mode. Create unique habitats and vast landscapes, make big decisions and meaningful choices, and nurture your animals as you construct and manage the world’s wildest zoos.\r\n"
				+ "\r\n" + "\r\n" + "\r\n"
				+ "Meet a world of incredible animals. From playful lion cubs to mighty elephants, every animal in Planet Zoo is a thinking, feeling individual with a distinctive look and personality of their own. Craft detailed habitats to bring your animals’ natural environments home, research and manage each species to allow them to thrive, and help your animals raise families to pass their genes onto future generations.\r\n"
				+ "\r\n" + "\r\n" + "\r\n"
				+ "Manage an amazing living world that responds to every decision you make. Focus on the big picture or go hands-on and control the smallest details. Thrill visitors with iconic exhibits, develop your zoo with new research, and release new generations of your animals back into the wild. Your choices come alive in a world where animal welfare and conservation comes first.\r\n"
				+ "\r\n" + "\r\n" + "\r\n"
				+ "Planet Zoo’s powerful piece-by-piece construction tools let you effortlessly make your zoo unique. Every creative decision you make impacts the lives of your animals and the experience of your visitors. Let your imagination run wild as you dig lakes and rivers, raise hills and mountains, carve paths and caves, and build stunning zoos with a choice of unique themes and hundreds of building components.\r\n"
				+ "\r\n" + "\r\n" + "\r\n"
				+ "Join a connected community and share the world’s most creative habitats, scenery and even whole zoos on the Steam Workshop. See your own designs appear in zoos around the world, or discover fresh new content from the Planet Zoo community every day.','Frontier Developments','Frontier Developments','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/TAvzq-HrSoQ\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>','https://nowcdkey.com/wp-content/uploads/2019/11/cover-nowcdkey2019-1.jpg','2019-11-05','Yes')"
				+ ",(18,'249.95','Total War: THREE KINGDOMS','Total War: THREE KINGDOMS is the first in the multi award-winning strategy series to recreate epic conflict across ancient China. Combining a gripping turn-based campaign game of empire-building, statecraft and conquest with stunning real-time battles, Total War: THREE KINGDOMS redefines the series in an age of heroes and legends.\r\n"
				+ "\r\n" + "\r\n" + "\r\n" + "China in 190CE\r\n" + "\r\n"
				+ "Welcome to a new era of legendary conquest.\r\n" + "\r\n"
				+ "This beautiful but fractured land calls out for a new emperor and a new way of life. Unite China under your rule, forge the next great dynasty, and build a legacy that will last through the ages.\r\n"
				+ "Choose from a cast of 12 legendary Warlords and conquer the realm. Recruit heroic characters to aide your cause and dominate your enemies on military, technological, political, and economic fronts.\r\n"
				+ "\r\n"
				+ "Will you build powerful friendships, form brotherly alliances, and earn the respect of your many foes? Or would you rather commit acts of treachery, inflict heart-wrenching betrayals, and become a master of grand political intrigue?\r\n"
				+ "\r\n" + "Your legend is yet to be written, but one thing is certain: glorious conquest awaits.\r\n"
				+ "ANCIENT CHINA RECREATED\r\n" + "\r\n" + "\r\n" + "\r\n"
				+ "Discover Three Kingdoms China, a land of breath-taking natural beauty. Battle across lush subtropics, arid deserts and snow-capped mountains. Marvel at legendary landmarks like the Great Wall of China and the Yangtze River. Explore the length and breadth of ancient China as you restore harmony to its embattled landscape.\r\n"
				+ "CHINA’S GREATEST LEGENDS\r\n" + "\r\n" + "\r\n" + "\r\n"
				+ "Forge a new empire as one of 12 legendary Warlords drawn from China’s celebrated historical epic, the Romance of the Three Kingdoms. Peerless commanders, powerful warriors and eminent statesmen, these characters each have a unique playstyle and objectives. Recruit an epic supporting cast of heroes to command your armies, govern your provinces and strengthen your growing empire. Characters are the beating heart of the game, and China’s very future will be shaped by its champions.\r\n"
				+ "GUANXI SYSTEM\r\n" + "\r\n" + "\r\n" + "\r\n"
				+ "Modelled on Guanxi, the Chinese concept of dynamic inter-relationships, Total War: THREE KINGDOMS takes a paradigm-shifting approach to character agency, with iconic, larger-than-life heroes and their relationships defining the future of ancient China. Each of these characters is brought to life with their own unique personality, motivations, and likes/dislikes. They also form their own deep relationships with each other, both positive and negative, that shape how your story plays out.\r\n"
				+ "ARTISTIC PURITY\r\n" + "\r\n" + "\r\n" + "\r\n"
				+ "With stunning visuals and flamboyant Wushu combat, THREE KINGDOMS is the art of war. With beautiful UI, vibrant vistas and authentic Chinese-inspired artwork, this reimagining of ancient China is a visual feast.\r\n"
				+ "REAL-TIME & TURN-BASED HARMONY\r\n" + "\r\n" + "\r\n" + "\r\n"
				+ "The turn-based campaign and real-time battles of Total War: THREE KINGDOMS are more interconnected than ever before. Actions in battle now have much greater consequences, affecting your Heroes’ relationship towards you, as well as the friendships and rivalries they develop with other characters. In a world where powerful allies are one of the keys to success, this adds a brand-new element to how victory is achieved.','CREATIVE ASSEMBLY, Feral Interactive (Mac), Feral Interactive (Linux)','SEGA, Feral Interactive (Mac), Feral Interactive (Linux)','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/aOP1YugqRDk\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>','https://upload.wikimedia.org/wikipedia/en/3/32/Total_War_Three_Kingdoms_cover_art.jpg','2019-05-23','Yes'),"
				+ "(19,'140.76','Stellaris','Get ready to explore, discover and interact with a multitude of species as you journey among the stars. Forge a galactic empire by sending out science ships to survey and explore, while construction ships build stations around newly discovered planets. Discover buried treasures and galactic wonders as you spin a direction for your society, creating limitations and evolutions for your explorers. Alliances will form and wars will be declared.\r\n"
				+ "\r\n"
				+ "Like all our Grand Strategy games, the adventure evolves with time. Because free updates are a part of any active Paradox game, you can continue to grow and expand your empire with new technologies and capabilities. What will you find beyond the stars? Only you can answer that.\r\n"
				+ "DEEP AND VARIED EXPLORATION\r\n"
				+ "Every game begins with a civilization that has just discovered the means to travel between stars and is ready to explore the galaxy. Have your science ships survey and explore anomalies, leading you into a myriad of quests, introducing strange worlds with even stranger stories and discoveries that may completely change your outcome.\r\n"
				+ "STUNNING SPACE VISUALS\r\n"
				+ "With characteristically complex unique planets and celestial bodies, you will enter a whirlwind of spectacles in a highly detailed universe.\r\n"
				+ "INFINITE VARIATION OF SPECIES AND ADVANCED DIPLOMACY\r\n"
				+ "Through customization and procedural generation, you will encounter infinitely varied races. Choose positive or negative traits, specific ideologies, limitations, evolutions or anything you can imagine. Interact with others through the advanced diplomacy system. Diplomacy is key in a proper grand strategy adventure. Adjust your strategy to your situation through negotiation and skill.\r\n"
				+ "INTERSTELLAR WARFARE\r\n"
				+ "An eternal cycle of war, diplomacy, suspicions and alliances await you. Defend or attack with fully customizable war fleets, where adaptation is the key to victory. Choose from an array of complex technologies when designing and customizing your ships with the complex ship designer. You have a multitude of capabilities to choose from to meet the unknown quests that await.\r\n"
				+ "ENORMOUS PROCEDURAL GALAXIES\r\n"
				+ "Grow and expand your empire with thousands of randomly generated planet types, galaxies, quests and monsters lurking in space.\r\n"
				+ "PLAY THE WAY YOU WANT\r\n"
				+ "Customize your Empire! The characters you choose, be it a murderous mushroom society or an engineering reptile race, can be customized with traits like ethics, type of technology, form of preferred space travel, type of habitat, philosophies and more. The direction of the game is based on your choices.','Paradox Development Studio','Paradox Interactive','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/zL0kemiI0yc\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>','https://cdn-cf.gamivo.com/image_cover.jpg?f=1281&n=0bb09c50-7850-11e7-8203-abacaa3272cd.jpg&h=2700aa414f76b5716031b9d4b1445ecd','2016-05-09','Yes'),"
				+ "(20,'55.95','Plague Inc: Evolved','Plague Inc: Evolved is a unique mix of high strategy and terrifyingly realistic simulation. Your pathogen has just infected ''Patient Zero'' - now you must bring about the end of human history by evolving a deadly, global Plague whilst adapting against everything humanity can do to defend itself.\r\n"
				+ "\r\n"
				+ "Plague Inc. is so realistic that the CDC even asked the developer to come and speak about the infection models in the game! http://blogs.cdc.gov/publichealthmatters/2013/04/plague-inc/.\r\n"
				+ "\r\n"
				+ "Over 120 million players have been infected by Plague Inc. already. Now, Plague Inc: Evolved combines the original critically acclaimed gameplay with significant, all-new features for PC including multiplayer, user-generated content support, improved graphics and lots more.','Ndemic Creations','Ndemic Creations','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/pSat_gLDXPc\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>','https://static-cdn.jtvnw.net/ttv-boxart/Plague%20Inc%3A%20Evolved.jpg','2016-02-18','Yes'),"
				+ "(21,'99.99','eFootball PES 2020','Experience the most realistic and authentic soccer game with eFootball PES 2020, winner of the ''E3 Best Sports Game'' award! Play with the biggest teams in world soccer, featuring Spanish champions FC Barcelona, global giants Manchester United, German champions FC Bayern Munchen, and Italian champions Juventus — who feature exclusively in PES!\r\n"
				+ "','Konami Digital Entertainment','Konami Digital Entertainment','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/_89i5H1zAeU\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>','https://i.ytimg.com/vi/d_KQO151F4Y/maxresdefault.jpg','2019-09-10','Yes'),"
				+ "(22,'189.00','Football Manager 2020','Run your football club, your way. Every decision counts in Football Manager 2020 with new features and polished game mechanics rewarding planning and progression like never before, empowering managers to develop and refine both your club’s and your own unique identity.\r\n"
				+ "\r\n"
				+ "Walk down the tunnel to a living, breathing football world with you at the very heart of it. Around here, your opinion matters!\r\n"
				+ "\r\n"
				+ "This is a world that rewards planning and knowledge but, unlike other games, there’s no pre-defined ending or script to follow – just endless possibilities and opportunities. Every club has a story to tell and it’s down to you to create it.\r\n"
				+ "\r\n" + "They say football is a game of dreams. Well, managers are a special breed of dreamers.\r\n"
				+ "\r\n"
				+ "They don’t see problems, only opportunities: the chance to prove themselves against the best in the world, to develop and instil a new footballing philosophy, to nurture talent through the ranks, to lift the club to greater heights and end the wait for silverware.\r\n"
				+ "\r\n"
				+ "How you get to the top is up to you… you’ll own your decisions, and the consequences they bring…\r\n"
				+ "\r\n" + "Base yourself in 50 of the biggest footballing countries worldwide\r\n"
				+ "Oversee a new era of success at one of 2,500 clubs at every level\r\n"
				+ "Sign the best and mould the future – scout more than 500,000 real players and staff\r\n"
				+ "Create your tactical vision and bring it to life on the training pitch\r\n"
				+ "Kick every ball with our most immersive and smartest match engine to date','Sports Interactive','SEGA','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/FhZnNPHTzX8\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>','https://www.epingi.com/wp-content/uploads/2020/03/Football-Manager-2020-PC-Unlocked-Version-Download-Full-Free-Game-Setup.jpg','2019-11-19','Yes'),"
				+ "(23,'56.98','RIDE 3','Feel the adrenaline and experience the most complete racing ever with RIDE 3!\r\n"
				+ "Immerse yourself in a modern 3D environment where you will live side by side with your bike, modifying it mechanically and aesthetically thanks to the new Livery Editor, which will let your dreams run wild. Before starting, don''t forget to customise your rider with the right outfit.\r\n"
				+ "THE BIKES\r\n"
				+ "More than 230 bike models available from the very first day, with more than 70 new models never seen before in a RIDE game. 30 different brands, both historic and contemporary, of which 9 are completely new. 7 different categories to satisfy every taste and riding style.\r\n"
				+ "THE TRACKS\r\n"
				+ "RIDE 3 will be a journey around the globe thanks to its 30 different tracks created from zero and faithfully reproduced via photogrammetry and drone scanning. Hurtle down breathtaking roads all around the world, including GP tracks, Road and Supermoto tracks, enjoy the panoramas of street and country tracks and compete in street and acceleration races.\r\n"
				+ "LIVERY EDITOR\r\n"
				+ "Choose your favourite bike and make it unique, just like your style. Create your livery from scratch, choose a background colour and an endless amount of 2D shapes, designs and effects. Work on various levels to create infinite compositions and find the aesthetic which works for you. Riding a bike is great. Riding a bike which you''ve created yourself is fantastic. Seeing other riders and players from all over the world racing in your livery is simply the best!\r\n"
				+ "CAREER\r\n"
				+ "The new career will be a Single Player experience which has never been seen before. Forget the old linear progression: the new Volumes-based system will allow you to collect, read and participate in races and competitions through more than 50 magazines. Each one will catapult you into a unique game experience, made up of competitions and anecdotes about the motorcycling world, which will improve your experience in RIDE 3.\r\n"
				+ "\r\n"
				+ "What are you waiting for? Start your adventure with RIDE 3.','Milestone S.r.l.','Milestone S.r.l.','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/TAV0dV79W6M\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>','https://steamcdn-a.akamaihd.net/steam/apps/759740/capsule_616x353.jpg?t=1553501167','2018-11-30','Yes'),"
				+ "(24,'224.95','F1 2019','The official videogame of the 2019 FIA FORMULA ONE WORLD CHAMPIONSHIP™, F1 2019 challenges you to Defeat your Rivals in the most ambitious F1 game in Codemasters’ history.\r\n"
				+ "\r\n" + "\r\n" + "\r\n"
				+ "F1 2019 features all the official teams, drivers and all 21 circuits from the 2019 season. This year sees the inclusion of F2™ with players being able to complete the 2018 season with the likes of George Russell, Lando Norris and Alexander Albon.\r\n"
				+ "\r\n" + "\r\n" + "\r\n"
				+ "With greater emphasis on graphical fidelity, the environments have been significantly enhanced, and the tracks come to life like never before. Night races have been completely overhauled creating vastly improved levels of realism and the upgraded F1 broadcast sound and visuals add further realism to all aspects of the race weekend.\r\n"
				+ "\r\n" + "THE OFFICIAL VIDEOGAME OF THE 2019 FORMULA ONE WORLD CHAMPIONSHIP™\r\n"
				+ "RISE UP AGAINST YOUR RIVALS\r\n"
				+ "NEW - F2™ career opening – establish your reputation and defeat your rivals before stepping up into the F1 Championship.\r\n"
				+ "NEW - Customise your own livery and lead the pack in weekly challenges or in online leagues. Test yourself against the very best in the dedicated F1 Esports area.\r\n"
				+ "IMPROVED - Replay and share your finest moments with the new automated race highlights feature, while stunning new night lighting and official F1 branding add even more realism.\r\n"
				+ "\r\n" + "\r\n" + "\r\n"
				+ "Online connection required to download the final F1 teams 2019 cars (as applicable) and F2™ 2019 season content.','Codemasters','Codemasters','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/iz7AMaJarQ8\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>','https://www.essentiallysports.com/wp-content/uploads/F1-2019-Game-Review-The-series-best-release-yet-is-much-more-than-better-visuals-787039-1280x720.jpg','2019-06-27','Yes'),"
				+ "(25,'29.50','Golf With Your Friends','Why have friends if not to play Golf... With Your Friends! Nothing is out of bounds as you take on 9 courses filled with fast paced, exciting, simultaneous mini golf for up to 12 players! The Par-Tee doesn''t stop there, players can expect additional courses and content throughout early access!','Blacklight Interactive','Team17 Digital Ltd','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/dbyZze_lbcs\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>','https://www.team17.com/wp-content/uploads/2019/02/FRI_NEWSSTORY_BY_001.jpg','2016-06-30','Yes'),"
				+ "(26,'110.95','R.B.I. Baseball 20','Legends. Start. Here. Unleash greatness with your MLB crew in R.B.I. Baseball 20. R.B.I. 20 redefines arcade baseball action with major advancements & improvements.\r\n"
				+ "\r\n"
				+ "• All-New Controls: Choose pitch types based on real pitcher data, power up to swing for the fences or play it safe for contact & streamlined baserunning controls\r\n"
				+ "• Pitcher’s Perspective: Brand new broadcast-inspired Pitching Camera gives you a new perspective when delivering pitches\r\n"
				+ "• Massive Player Model Improvements: Revamped hair & added more details including eye black, pine tar on helmets & dirt & grass stains after dives & slides\r\n"
				+ "• More Authenticity: Improved stealing & pick-offs, revamped MLB ballparks, tailored cutscene animations & camera angles, better crowd behaviors & improved ball collision\r\n"
				+ "• All Your Favorite Modes: Franchise, Exhibition & Home Run Derby\r\n"
				+ "• Play As The Greats: 165+ MLB Legends & Legend Teams\r\n"
				+ "• Weekly Updates: Up-to-date Rosters & Stats\r\n"
				+ "• Groovy soundtrack: Songs from 20 artists','MLB','MLB','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/Izl-2MVrBv0\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>','https://cdn02.nintendo-europe.com/media/images/10_share_images/games_15/nintendo_switch_download_software_1/H2x1_NSwitchDS_RBIBaseball20_image1600w.jpg','2020-03-17','Yes'),"
				+ "(27,'240.00','Borderlands 3','The original shooter-looter returns, packing bazillions of guns and a mayhem-fueled adventure! Blast through new worlds & enemies and save your home from the most ruthless cult leaders in the galaxy.','Gearbox Software','2K','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/d9Gu1PspA3Y\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>','https://cdn.2kgames.com/borderlands/news/Article+Embeds/PAXE20/steam-art_560.jpg','2020-03-13','Yes'),"
				+ "(28,'259.00','DOOM Eternal','Hell’s armies have invaded Earth. Become the Slayer in an epic single-player campaign to conquer demons across dimensions and stop the final destruction of humanity.\r\n"
				+ "The Only Thing they Fear... Is You.\r\n"
				+ "Experience the ultimate combination of speed and power in DOOM Eternal - the next leap in push-forward, first-person combat.\r\n"
				+ "Slayer Threat Level At Maximum\r\n"
				+ "Armed with a shoulder-mounted flamethrower, retractable wrist-mounted blade, upgraded guns and mods, and abilities, you''re faster, stronger, and more versatile than ever.\r\n"
				+ "Unholy Trinity\r\n"
				+ "Take what you need from your enemies: Glory kill for extra health, incinerate for armor, and chainsaw demons to stock up on ammo to become the ultimate demon-slayer.\r\n"
				+ "Enter Battlemode\r\n"
				+ "A new 2 versus 1 multiplayer experience. A fully-armed DOOM Slayer faces off against two player-controlled demons, fighting it out in a best-of-five round match of intense first-person combat.','id Software','Bethesda Softworks','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/FkklG9MA0vM\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>','https://www.slashgear.com/wp-content/uploads/2020/03/doom_eternal_main.jpg','2020-03-20','Yes'),"
				+ "(29,'140.76','Hearts of Iron IV','Victory is at your fingertips! Your ability to lead your nation is your supreme weapon, the strategy game Hearts of Iron IV lets you take command of any nation in World War II; the most engaging conflict in world history.\r\n"
				+ "\r\n"
				+ "From the heart of the battlefield to the command center, you will guide your nation to glory and wage war, negotiate or invade. You hold the power to tip the very balance of WWII. It is time to show your ability as the greatest military leader in the world. Will you relive or change history? Will you change the fate of the world by achieving victory at all costs?\r\n"
				+ "\r\n" + "Main Features:\r\n" + "Total strategic war:\r\n"
				+ "War is not only won on land, sea and in the air. It’s also achieved in the hearts and minds of men and women.\r\n"
				+ "Authentic real-time war simulation:\r\n"
				+ "Let the greatest commanders of WW2 fight your war with the tools of the time; tanks, planes, ships, guns and newly discovered weapons of mass destruction.\r\n"
				+ "Assume control of any nation:\r\n"
				+ "Choose from the greatest powers striving for victory, or the small nations trying to weather the storm.\r\n"
				+ "Turn the world into your battlefield: Experience the full WWII timespan in a topographical map complete with seasons, weather and terrain. Snow, mud, storms can be both your strong ally and a ruthless enemy.\r\n"
				+ "Negotiate or force your will:\r\n"
				+ "Experience the advanced politics and diplomacy systems, form factions, engage in trade for resources and appoint ministers to your party.\r\n"
				+ "Intense Online Combat:\r\n"
				+ "Battle in both competitive and cooperative multiplayer for up to 32 players. Featuring cross-platform multiplayer.\r\n"
				+ "Give your nation a unique edge:\r\n"
				+ "Experience the flexible technology system, where all major powers get their own unique identity. Develop detailed historic tanks and planes through research and army experience.\r\n"
				+ "Everyone will receive:\r\n" + "Poland: United and Ready:\r\n"
				+ "A Free DLC adds a unique focus tree for Poland, new 3d models for tanks and planes, 2d assets, and extra leader portraits for the ultimate in historical accuracy.\r\n"
				+ "Forum Avatar\r\n" + "Forum avatar for the Paradox forum.\r\n"
				+ "Wallpaper','Paradox Development Studio','Paradox Interactive','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/e9BiUtINy4w\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>','https://im9.cz/iR/importprodukt-orig/8fb/8fba6d09b8a5347672100abe865b4601.jpg','2016-06-06','Yes'),"
				+ "(30,'189.95','DJMAX RESPECT V','DJMAX RESPECT V delivers an unrivaled rhythm game experience, introducing new modes, new artists, more than 150 unique tracks, exclusive music videos, and for the first time in franchise history, competitive online-multiplayer!\r\n"
				+ "\r\n"
				+ "Experience new tracks and music videos from world renowned artists like Marshmello, Porter Robinson, and Yukika, as well as the return of friendly names including BEXTER, ND LEE, Paul Bazooka, and Makou, among others.\r\n"
				+ "\r\n"
				+ "Whether you crave a casual freeplay session, or desire to crush your friend’s recent high score in real time, DJMAX RESPECT V has you covered. ‘AIR Mode’ will allow you to enjoy a continuous, random playlist, during which you can choose to play or simply listen, as well as leave comments for other players! But if bragging rights and glory are what you crave, new ‘Online Modes’ will put your skills to the test, pairing you against friends and rivals around the world.\r\n"
				+ "\r\n"
				+ "Featuring both keyboard and full controller support (Coming Soon™), and an extensive tracklist spanning numerous genres, like Pop, Rock, Electronic, Ambient, Jazz and even Easy Listening, DJMAX RESPECT V will leave no rhythm gamer wanting!','NEOWIZ','NEOWIZ','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/DkXdltIqkng\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>','https://steamcdn-a.akamaihd.net/steam/apps/960170/capsule_616x353.jpg?t=1576720949','2020-03-12','Yes');";

		statement.executeUpdate(insertToTable);
	}

	// Insert values to Orders table
	public static void createOrdersDb(Connection conn) throws Exception {

		Statement statement = (Statement) conn.createStatement();
		String insertToTable = "INSERT INTO Orders(numOrder,idClient) VALUES"
				+ "(1,'3'),(2,'3'),(3,'4'),(4,'13'),(5,'14'),(6,'15'),(7,'13'),(8,'5'),(9,'6'),(10,'13'),(11,'15'),(12,'14'),(13,'16')"
				+ ",(14,'17'),(15,'18'),(16,'19'),(17,'20'),(18,'21'),(19,'22'),(20,'23'),(21,'24'),(22,'25');";
		statement.executeUpdate(insertToTable);
	}

	// Insert values to OrderLines table
	public static void createOrderLinesDb(Connection conn) throws Exception {

		Statement statement = (Statement) conn.createStatement();
		String insertToTable = "INSERT INTO OrderLines(numOrder,gameId) VALUES"
				+ "(1,'1'),(2,'2'),(2,'4'),(3,'6'),(4,'16'),(4,'17'),"
				+ "(5,'16'),(5,'17'),(6,'16'),(7,'18'),(8,'12'),(8,'29'),(9,'29'),(10,'13'),(10,'29'),(11,'29'),(10,'7'),(11,'7'),(12,'29'),"
				+ "(13,'1'),(13,'2'),(13,'3'),(13,'4'),(13,'5'),(13,'6'),(13,'7'),(13,'9'),(13,'11'),(13,'13'),(13,'14'),(13,'16')"
				+ ",(13,'19'),(13,'20'),(13,'21'),(13,'22'),(13,'23'),(13,'26'),(13,'29'),(14,'1'),(14,'3'),(14,'5'),(14,'6'),(14,'9')"
				+ ",(14,'11'),(14,'13'),(14,'14'),(14,'16'),(14,'19'),(14,'20'),(14,'21'),(14,'22'),(14,'23'),(14,'26'),(14,'29')"
				+ ",(15,'2'),(15,'3'),(15,'5'),(15,'7'),(15,'9'),(15,'11'),(15,'13'),(15,'15'),(15,'16'),(15,'18'),(15,'20')"
				+ ",(15,'22'),(15,'24'),(15,'26'),(15,'29'),(16,'1'),(16,'3'),(16,'6'),(16,'7'),(16,'9'),(16,'10'),(16,'12')"
				+ ",(16,'13'),(16,'16'),(16,'17'),(16,'21'),(16,'22'),(16,'24'),(16,'26'),(16,'27'),(16,'29'),(16,'30'),(17,'3')"
				+ ",(17,'4'),(17,'5'),(17,'7'),(17,'8'),(17,'9'),(17,'11'),(17,'12'),(17,'14'),(17,'16'),(17,'18'),(17,'20')"
				+ ",(17,'21'),(17,'22'),(17,'24'),(17,'26'),(17,'27'),(17,'29'),(17,'30'),(18,'1'),(18,'2'),(18,'3'),(18,'4')"
				+ ",(18,'6'),(18,'7'),(18,'8'),(18,'9'),(18,'10'),(18,'12'),(18,'13'),(18,'14'),(18,'16'),(18,'19'),(18,'20')"
				+ ",(18,'21'),(18,'22'),(18,'27'),(18,'30'),(19,'2'),(19,'3'),(19,'4'),(19,'7'),(19,'9'),(19,'12'),(19,'13')"
				+ ",(19,'14'),(19,'16'),(19,'17'),(19,'18'),(19,'19'),(19,'20'),(19,'21'),(19,'22'),(19,'23'),(19,'24'),(19,'27')"
				+ ",(19,'30'),(20,'1'),(20,'2'),(20,'5'),(20,'6'),(20,'7'),(20,'8'),(20,'10'),(20,'11'),(20,'12'),(20,'13')"
				+ ",(20,'14'),(20,'15'),(20,'16'),(20,'19'),(20,'20'),(20,'23'),(20,'24'),(20,'28'),(20,'29'),(20,'30')"
				+ ",(21,'1'),(21,'2'),(21,'3'),(21,'4'),(21,'6'),(21,'8'),(21,'9'),(21,'10'),(21,'11'),(21,'12')"
				+ ",(21,'13'),(21,'15'),(21,'16'),(21,'17'),(21,'20'),(21,'21'),(21,'23'),(21,'24'),(21,'25'),(21,'27')"
				+ ",(22,'1'),(22,'2'),(22,'3'),(22,'4'),(22,'5'),(22,'7'),(22,'8'),(22,'9'),(22,'11'),(22,'12')"
				+ ",(22,'13'),(22,'14'),(22,'17'),(22,'18'),(22,'19'),(22,'20'),(22,'21'),(22,'23'),(22,'25'),(22,'28')"
				+ ",(22,'29'),(22,'30');";
		statement.executeUpdate(insertToTable);
	}

	// Insert values to Genre table
	public static void createGenreDb(Connection conn) throws Exception {

		Statement statement = (Statement) conn.createStatement();
		String insertToTable = "INSERT INTO Genre(genre,gameId) VALUES" + "('Action',1)" + ",('Adventure',1)"
				+ ",('Action',2)" + ",('Adventure',2)" + ",('Action',3)" + ",('RPG',3)" + ",('RPG',4)" + ",('Action',5)"
				+ ",('Adventure',5)" + ",('Strategy',6)" + ",('Sport',7)" + ",('Action',7)" + ",('Sport',8)"
				+ ",('Strategy',9)"
				+ ",('RPG',10),('RPG',11),('Action',11),('Adventure',11),('RPG',12),('Action',12),('Adventure',12),('Action',13),('Adventure',13)"
				+ ",('Action',14),('Adventure',14),('Strategy',15),('Strategy',16),('Strategy',17),('Strategy',18),('Strategy',19),('Strategy',20)"
				+ ",('Sport',21),('Sport',22),('Sport',23),('Sport',24),('Sport',25),('Sport',26),('RPG',27),('Action',27),('Action',28),('Strategy',29),('Sport',30);";
		statement.executeUpdate(insertToTable);
	}

	// Insert values to OrderLines table
	public static void createRatingDb(Connection conn) throws Exception {

		Statement statement = (Statement) conn.createStatement();
		String insertToTable = "INSERT INTO Rating(idClient,gameId,rating) VALUES"
				+ "(3,1,0),(3,2,0),(3,3,0),(3,4,0),(3,5,0),(3,6,0),(3,7,0),(3,8,0),(3,9,0),(3,10,0)"
				+ ",(3,11,3),(3,12,0),(3,13,0),(3,14,0),(3,15,0),(3,16,0),(3,17,0),(3,18,0),(3,19,0),(3,20,0)"
				+ ",(3,21,0),(3,22,0),(3,23,0),(3,24,0),(3,25,0),(3,26,0),(3,27,0),(3,28,0),(3,29,0),(3,30,0)"
				+ ",(4,1,0),(4,2,0),(4,3,0),(4,4,0),(4,5,0),(4,6,4.5),(4,7,0),(4,8,0),(4,9,0),(4,10,0)"
				+ ",(4,11,3),(4,12,0),(4,13,0),(4,14,0),(4,15,0),(4,16,0),(4,17,0),(4,18,0),(4,19,0),(4,20,0)"
				+ ",(4,21,4.5),(4,22,0),(4,23,0),(4,24,0),(4,25,0),(4,26,0),(4,27,0),(4,28,0),(4,29,1),(4,30,0)"
				+ ",(5,1,0),(5,2,0),(5,3,0),(5,4,0),(5,5,0),(5,6,0),(5,7,0),(5,8,0),(5,9,0),(5,10,0)"
				+ ",(5,11,0),(5,12,5),(5,13,0),(5,14,0),(5,15,0),(5,16,3),(5,17,0),(5,18,0),(5,19,0),(5,20,0)"
				+ ",(5,21,0),(5,22,0),(5,23,0),(5,24,0),(5,25,0),(5,26,0),(5,27,0),(5,28,0),(5,29,1),(5,30,0)"
				+ ",(6,1,0),(6,2,0),(6,3,0),(6,4,0),(6,5,0),(6,6,0),(6,7,0),(6,8,0),(6,9,0),(6,10,0)"
				+ ",(6,11,0),(6,12,0),(6,13,5),(6,14,0),(6,15,0),(6,16,3),(6,17,0),(6,18,0),(6,19,0),(6,20,0)"
				+ ",(6,21,0),(6,22,0),(6,23,0),(6,24,0),(6,25,0),(6,26,0),(6,27,0),(6,28,0),(6,29,1),(6,30,0)"
				+ ",(7,1,0),(7,2,0),(7,3,0),(7,4,0),(7,5,0),(7,6,0),(7,7,0),(7,8,0),(7,9,0),(7,10,0)"
				+ ",(7,11,0),(7,12,0),(7,13,0),(7,14,0),(7,15,0),(7,16,0),(7,17,0),(7,18,0),(7,19,0),(7,20,0)"
				+ ",(7,21,0),(7,22,0),(7,23,0),(7,24,0),(7,25,0),(7,26,0),(7,27,0),(7,28,0),(7,29,0),(7,30,0)"
				+ ",(8,1,0),(8,2,0),(8,3,0),(8,4,0),(8,5,0),(8,6,0),(8,7,0),(8,8,0),(8,9,0),(8,10,0)"
				+ ",(8,11,0),(8,12,0),(8,13,0),(8,14,0),(8,15,0),(8,16,0),(8,17,0),(8,18,0),(8,19,0),(8,20,0)"
				+ ",(8,21,0),(8,22,0),(8,23,0),(8,24,0),(8,25,0),(8,26,0),(8,27,0),(8,28,0),(8,29,0),(8,30,0)"
				+ ",(9,1,0),(9,2,0),(9,3,0),(9,4,0),(9,5,0),(9,6,0),(9,7,0),(9,8,0),(9,9,0),(9,10,0)"
				+ ",(9,11,0),(9,12,0),(9,13,0),(9,14,0),(9,15,0),(9,16,0),(9,17,0),(9,18,0),(9,19,0),(9,20,0)"
				+ ",(9,21,0),(9,22,0),(9,23,0),(9,24,0),(9,25,0),(9,26,0),(9,27,0),(9,28,0),(9,29,0),(9,30,0)"
				+ ",(10,1,0),(10,2,0),(10,3,0),(10,4,0),(10,5,0),(10,6,0),(10,7,0),(10,8,0),(10,9,0),(10,10,0)"
				+ ",(10,11,0),(10,12,0),(10,13,0),(10,14,0),(10,15,0),(10,16,0),(10,17,0),(10,18,0),(10,19,0),(10,20,0)"
				+ ",(10,21,0),(10,22,0),(10,23,0),(10,24,0),(10,25,0),(10,26,0),(10,27,0),(10,28,0),(10,29,0),(10,30,0)"
				+ ",(11,1,0),(11,2,0),(11,3,0),(11,4,0),(11,5,0),(11,6,0),(11,7,0),(11,8,0),(11,9,0),(11,10,0)"
				+ ",(11,11,0),(11,12,0),(11,13,0),(11,14,0),(11,15,0),(11,16,0),(11,17,0),(11,18,0),(11,19,0),(11,20,0)"
				+ ",(11,21,0),(11,22,0),(11,23,0),(11,24,0),(11,25,0),(11,26,0),(11,27,0),(11,28,0),(11,29,0),(11,30,0)"
				+ ",(12,1,0),(12,2,0),(12,3,0),(12,4,0),(12,5,0),(12,6,0),(12,7,0),(12,8,0),(12,9,0),(12,10,0)"
				+ ",(12,11,0),(12,12,0),(12,13,0),(12,14,0),(12,15,0),(12,16,0),(12,17,0),(12,18,0),(12,19,0),(12,20,0)"
				+ ",(12,21,0),(12,22,0),(12,23,0),(12,24,0),(12,25,0),(12,26,0),(12,27,0),(12,28,0),(12,29,0),(12,30,0)"
				+ ",(13,1,0),(13,2,0),(13,3,0),(13,4,0),(13,5,0),(13,6,0),(13,7,5),(13,8,0),(13,9,0),(13,10,0)"
				+ ",(13,11,0),(13,12,0),(13,13,0),(13,14,0),(13,15,0),(13,16,5),(13,17,4),(13,18,3.5),(13,19,0),(13,20,0)"
				+ ",(13,21,0),(13,22,0),(13,23,0),(13,24,0),(13,25,0),(13,26,0),(13,27,0),(13,28,0),(13,29,5),(13,30,0)"
				+ ",(14,1,0),(14,2,0),(14,3,0),(14,4,0),(14,5,0),(14,6,0),(14,7,0),(14,8,0),(14,9,0),(14,10,0)"
				+ ",(14,11,0),(14,12,0),(14,13,0),(14,14,0),(14,15,0),(14,16,5),(14,17,4),(14,18,0),(14,19,0),(14,20,0)"
				+ ",(14,21,0),(14,22,0),(14,23,0),(14,24,0),(14,25,0),(14,26,0),(14,27,0),(14,28,0),(14,29,5),(14,30,0)"
				+ ",(15,1,0),(15,2,0),(15,3,0),(15,4,0),(15,5,0),(15,6,0),(15,7,5),(15,8,0),(15,9,0),(15,10,0)"
				+ ",(15,11,0),(15,12,0),(15,13,0),(15,14,0),(15,15,0),(15,16,5),(15,17,0),(15,18,0),(15,19,0),(15,20,0)"
				+ ",(15,21,0),(15,22,0),(15,23,0),(15,24,0),(15,25,0),(15,26,0),(15,27,0),(15,28,0),(15,29,5),(15,30,0)"
				+ ",(16,1,2),(16,2,3.5),(16,3,1),(16,4,5),(16,5,5),(16,6,4.5),(16,7,4),(16,8,0),(16,9,0),(16,10,0)"
				+ ",(16,11,3),(16,12,1),(16,13,0),(16,14,2),(16,15,2.5),(16,16,5),(16,17,5),(16,18,0),(16,19,0),(16,20,5)"
				+ ",(16,21,2),(16,22,1),(16,23,3.5),(16,24,2),(16,25,5),(16,26,0),(16,27,0),(16,28,1.5),(16,29,5),(16,30,3.5)"
				+ ",(17,1,1),(17,2,0),(17,3,1.5),(17,4,0),(17,5,5),(17,6,1),(17,7,0),(17,8,0),(17,9,2.5),(17,10,0)"
				+ ",(17,11,2.5),(17,12,0),(17,13,1),(17,14,2),(17,15,0),(17,16,1.5),(17,17,0),(17,18,0),(17,19,5),(17,20,5)"
				+ ",(17,21,3),(17,22,5),(17,23,2),(17,24,0),(17,25,0),(17,26,2),(17,27,0),(17,28,0),(17,29,5),(17,30,0)"
				+ ",(18,1,0),(18,2,5),(18,3,5),(18,4,0),(18,5,2.5),(18,6,0),(18,7,5),(18,8,0),(18,9,5),(18,10,0)"
				+ ",(18,11,4.5),(18,12,0),(18,13,2),(18,14,0),(18,15,2.5),(18,16,5),(18,17,0),(18,18,5),(18,19,0),(18,20,3)"
				+ ",(18,21,0),(18,22,1.5),(18,23,0),(18,24,1),(18,25,0),(18,26,3.5),(18,27,0),(18,28,0),(18,29,5),(18,30,0)"
				+ ",(19,1,5),(19,2,0),(19,3,5),(19,4,0),(19,5,0),(19,6,1),(19,7,5),(19,8,0),(19,9,0.5),(19,10,1)"
				+ ",(19,11,0),(19,12,5),(19,13,2),(19,14,0),(19,15,0),(19,16,5),(19,17,3),(19,18,0),(19,19,0),(19,20,0)"
				+ ",(19,21,3.5),(19,22,4),(19,23,0),(19,24,1.5),(19,25,0),(19,26,2.5),(19,27,0),(19,28,5),(19,29,5),(19,30,0)"
				+ ",(20,1,0),(20,2,0),(20,3,3),(20,4,0.5),(20,5,1),(20,6,0),(20,7,5),(20,8,2),(20,9,4.5),(20,10,0)"
				+ ",(20,11,5),(20,12,4),(20,13,0),(20,14,0.5),(20,15,0),(20,16,5),(20,17,0),(20,18,3.5),(20,19,0),(20,20,3.5)"
				+ ",(20,21,3),(20,22,5),(20,23,0),(20,24,2),(20,25,0),(20,26,1),(20,27,5),(20,28,0),(20,29,5),(20,30,5)"
				+ ",(21,1,4.5),(21,2,1),(21,3,5),(21,4,0.5),(21,5,0),(21,6,2),(21,7,1),(21,8,5),(21,9,4),(21,10,5)"
				+ ",(21,11,0),(21,12,2),(21,13,4),(21,14,5),(21,15,0),(21,16,5),(21,17,0),(21,18,0),(21,19,3.5),(21,20,2.5)"
				+ ",(21,21,3),(21,22,1.5),(21,23,0),(21,24,0),(21,25,0),(21,26,0),(21,27,5),(21,28,2),(21,29,5),(21,30,0)"
				+ ",(22,1,0),(22,2,1),(22,3,0.5),(22,4,4.5),(22,5,0),(22,6,0),(22,7,4.5),(22,8,0),(22,9,5),(22,10,0)"
				+ ",(22,11,0),(22,12,2),(22,13,0.5),(22,14,4.5),(22,15,0),(22,16,5),(22,17,3),(22,18,0.5),(22,19,4),(22,20,3.5)"
				+ ",(22,21,1.5),(22,22,5),(22,23,5),(22,24,2.5),(22,25,0),(22,26,0),(22,27,1),(22,28,0),(22,29,0),(22,30,2.5)"
				+ ",(23,1,2),(23,2,2),(23,3,0),(23,4,0),(23,5,5),(23,6,2),(23,7,4),(23,8,1.5),(23,9,0),(23,10,2.5)"
				+ ",(23,11,4),(23,12,5),(23,13,2.5),(23,14,2.5),(23,15,3),(23,16,5),(23,17,0),(23,18,0),(23,19,1.5),(23,20,4.5)"
				+ ",(23,21,0),(23,22,0),(23,23,5),(23,24,4.5),(23,25,0),(23,26,0),(23,27,0),(23,28,2),(23,29,3),(23,30,2.5)"
				+ ",(24,1,2),(24,2,1.5),(24,3,3),(24,4,3.5),(24,5,0),(24,6,4.5),(24,7,0),(24,8,4),(24,9,1.5),(24,10,5)"
				+ ",(24,11,1),(24,12,0.5),(24,13,5),(24,14,0),(24,15,1.5),(24,16,4),(24,17,2),(24,18,0),(24,19,0),(24,20,3.5)"
				+ ",(24,21,1),(24,22,0),(24,23,5),(24,24,4.5),(24,25,5),(24,26,0),(24,27,5),(24,28,0),(24,29,0),(24,30,0)"
				+ ",(25,1,5),(25,2,5),(25,3,1),(25,4,0.5),(25,5,5),(25,6,0),(25,7,1),(25,8,1),(25,9,3),(25,10,0)"
				+ ",(25,11,5),(25,12,4.5),(25,13,4),(25,14,2),(25,15,0),(25,16,0),(25,17,2),(25,18,4.5),(25,19,3.5),(25,20,5)"
				+ ",(25,21,5),(25,22,0),(25,23,1),(25,24,0),(25,25,5),(25,26,0),(25,27,0),(25,28,4),(25,29,5),(25,30,3.5);"
				;

		statement.executeUpdate(insertToTable);
	}

	// Just main so we could build our data base by executing java application
	public static void main(String[] args) {

	}

}
