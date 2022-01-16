-- Query: Title details for Animes
-- INNER JOIN: TITLES - TITLE_DETAILS: SELECT td.name, t.dtype FROM TITLE_DETAILS  td INNER JOIN TITLE t ON t.id = td.title_id;
-- INNER JOIN: TITLES - TITLE_DETAILS - COLLECTION: SELECT td.name, t.dtype FROM TITLE_DETAILS  td INNER JOIN TITLE t ON t.id = td.title_id INNER JOIN TITLE_COLLECTIONS tc ON tc.titles_id = t.id  INNER JOIN COLLECTION c ON c.id = tc.collections_id;
 


--- Queries for the titles: 
INSERT INTO COLLECTION VALUES (1,'C:\\Collection\Animes');

INSERT INTO TITLE_DETAILS (id, finished_date, image_cover, image_thumbnail, name, release_date, score, synopsis) values (1,TO_DATE('01-01-2015','dd-MM-yyyy'),null,null,'Naruto',TO_DATE('01-Jan-2000'),'7.5','Naruto is a ninja');
INSERT INTO TITLE VALUES ('Anime',1,'C:\\Collection\Animes\Naruto',1,1);
UPDATE TITLE_DETAILS
SET title_id= 1
WHERE id = 1;
INSERT INTO TITLE_COLLECTIONS VALUES (1,1);


INSERT INTO TITLE_DETAILS (id, finished_date, image_cover, image_thumbnail, name, release_date, score, synopsis) values (2,TO_DATE('01-01-2030','dd-MM-yyyy'),null,null,'One Piece',TO_DATE('01-Jan-1999'),'9.98','Luffy is a pirate');
INSERT INTO TITLE VALUES ('Anime',2,'C:\\Collection\Animes\One Piece',1,2);
UPDATE TITLE_DETAILS
SET title_id= 2
WHERE id = 2;
INSERT INTO TITLE_COLLECTIONS VALUES (2,1);


INSERT INTO TITLE_DETAILS (id, finished_date, image_cover, image_thumbnail, name, release_date, score, synopsis) values (3,TO_DATE('01-01-2018','dd-MM-yyyy'),null,null,'Dragon Ball',TO_DATE('01-Jan-1980'),'8.0','Goku is a sayajin');
INSERT INTO TITLE VALUES ('Anime',3,'C:\\Collection\Animes\Dragon Ball',1,3);
UPDATE TITLE_DETAILS
SET title_id= 3
WHERE id = 3;
INSERT INTO TITLE_COLLECTIONS VALUES (3,1);


INSERT INTO TITLE_DETAILS (id, finished_date, image_cover, image_thumbnail, name, release_date, score, synopsis) values (4,TO_DATE('01-01-2014','dd-MM-yyyy'),null,null,'Fairy Tail',TO_DATE('01-Jan-2006'),'5.5','Nastu is a mage');
INSERT INTO TITLE VALUES ('Anime',4,'C:\\Collection\Animes\Fairy Tail',1,4);
UPDATE TITLE_DETAILS
SET title_id= 4
WHERE id = 4;
INSERT INTO TITLE_COLLECTIONS VALUES (4,1);

INSERT INTO COLLECTION VALUES (2,'C:\\Collection\Mangas');
INSERT INTO TITLE_DETAILS (id, finished_date, image_cover, image_thumbnail, name, release_date, score, synopsis) values (5,TO_DATE('01-01-2026','dd-MM-yyyy'),null,null,'Vagabond',TO_DATE('01-Jan-2002'),'9.2','The sumurai way');
INSERT INTO TITLE VALUES ('Manga',5,'C:\\Collection\Mangas\Vagabond',2,5);
UPDATE TITLE_DETAILS
SET title_id= 5
WHERE id = 5;
INSERT INTO TITLE_COLLECTIONS VALUES (5,2);

--- Queries for the items: 
-- INNER JOIN: ITEMS - ITEM_DETAILS: SELECT i.dtype,itemd.name,i.path  FROM ITEM_DETAILS  itemd INNER JOIN ITEM i ON i.id = itemd.item_id;

INSERT INTO ITEM_DETAILS (id,name,official_name,original_name,release_date,slug) VALUES (1,'Naruto - Episode 01','Enter Naruto Uzumaki','[NINJA_FANSUB] Naruto EP 01 FULLHD',TO_DATE('01-01-2015','dd-MM-yyyy'),'naruto');
INSERT INTO ITEM (dtype, id, path, description, duration, anime_id,item_details_id) values ('Episode',1,'C:\\Collection\Animes\\Naruto\\Naruto - Episode 01.mp4','The begin, naruto is a ninja', '1380', 1,1);
UPDATE ITEM_DETAILS
SET item_id= 1
WHERE id = 1;

INSERT INTO ITEM_DETAILS (id,name,official_name,original_name,release_date,slug) VALUES (2,'One Piece - Episode 01','Enter Luffy','[PIRATE_FANSUB] One Piece EP 01 FULLHD',TO_DATE('01-01-2030','dd-MM-yyyy'),'one_piece');
INSERT INTO ITEM (dtype, id, path, description, duration, anime_id,item_details_id) values ('Episode',2,'C:\\Collection\Animes\\One Piece\\One Piece - Episode 01.mp4','The begin, luffy is a pirate', '1380', 2,2);
UPDATE ITEM_DETAILS
SET item_id= 2
WHERE id = 2;

INSERT INTO ITEM_DETAILS (id,name,official_name,original_name,release_date,slug) VALUES (3,'Dragon Ball - Episode 01','Enter Goku','[SAIJIN_FANSUB] Dragon Ball EP 01 FULLHD',TO_DATE('01-01-2018','dd-MM-yyyy'),'dragon_ball');
INSERT INTO ITEM (dtype, id, path, description, duration, anime_id,item_details_id) values ('Episode',3,'C:\\Collection\Animes\\Dragon Ball\\Dragon Ball - Episode 01.mp4','The begin, goku is a sayajin', '1380', 3,3);
UPDATE ITEM_DETAILS
SET item_id= 3
WHERE id = 3;

INSERT INTO ITEM_DETAILS (id,name,official_name,original_name,release_date,slug) VALUES (4,'Fairy Tail - Episode 01','Enter Nastu','[MAGE_FANSUB] Fairy Tail EP 01 FULLHD',TO_DATE('01-01-2014','dd-MM-yyyy'),'fairy_tail');
INSERT INTO ITEM (dtype, id, path, description, duration, anime_id,item_details_id) values ('Episode',4,'C:\\Collection\Animes\\Dragon Ball\\Dragon Ball - Episode 01.mp4','The begin, goku is a sayajin', '1380', 4,4);
UPDATE ITEM_DETAILS
SET item_id= 4
WHERE id = 4;

INSERT INTO ITEM_DETAILS (id,name,official_name,original_name,release_date,slug) VALUES (5,'Vagabond - Chapter 01','Enter Sumurai','[SUMURAI_FANSUB] Vagabond Chapter 01 FULLCOLOR',TO_DATE('01-01-2026','dd-MM-yyyy'),'vagabond');
INSERT INTO ITEM (dtype, id, path,description, manga_id,item_details_id) values ('Chapter',5,'C:\\Collection\Mangas\\Vagabond\\Vagabond - Chapter 01','The begin, the sumurai way', 5,5);
UPDATE ITEM_DETAILS
SET item_id= 5
WHERE id = 5;
