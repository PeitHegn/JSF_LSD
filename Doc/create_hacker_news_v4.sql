DROP TABLE IF EXISTS CONTRIBUTOR CASCADE;
CREATE TABLE CONTRIBUTOR(
contributor_id NUMERIC NOT NULL,
contributor_name VARCHAR(256) NOT NULL,
contributor_password VARCHAR(256) NOT NULL,
contributor_email VARCHAR(256),
score NUMERIC,
created_date DATE NOT NULL
);

DROP TABLE IF EXISTS POST CASCADE;
CREATE TABLE POST(
post_id NUMERIC NOT NULL,
post_title VARCHAR(256),
post_url VARCHAR(256),
score NUMERIC,
post_text VARCHAR(1024),
contributor_id NUMERIC NOT NULL,
post_type VARCHAR(8),
parent_id NUMERIC,
created_date DATE NOT NULL
);



ALTER TABLE ONLY CONTRIBUTOR ADD CONSTRAINT unique_contributor_name UNIQUE ("contributor_name");
ALTER TABLE ONLY CONTRIBUTOR ADD CONSTRAINT pk_contributor PRIMARY KEY ("contributor_id");
ALTER TABLE ONLY POST ADD CONSTRAINT pk_post PRIMARY KEY ("post_id");

ALTER TABLE POST ADD CONSTRAINT fk_post_contributor FOREIGN KEY (contributor_id) REFERENCES CONTRIBUTOR ("contributor_id") MATCH FULL;
ALTER TABLE POST ADD CONSTRAINT fk_parent FOREIGN KEY (parent_id) REFERENCES POST ("post_id") MATCH FULL;


DROP SEQUENCE IF EXISTS CONTRIBUTOR_SEQ;
DROP SEQUENCE IF EXISTS POST_SEQ;


CREATE SEQUENCE CONTRIBUTOR_SEQ START 1;
CREATE SEQUENCE POST_SEQ START 1;

INSERT INTO CONTRIBUTOR VALUES(nextval('CONTRIBUTOR_SEQ'), 'Henning', 'secret', 'hean@hean.com', 500, current_timestamp);
INSERT INTO CONTRIBUTOR VALUES(nextval('CONTRIBUTOR_SEQ'), 'Bjarne', 'secret', 'bjan@hean.com', 1000, current_timestamp);

INSERT INTO POST(post_id, post_title, post_url, score, contributor_id, post_type, created_date) VALUES(nextval('POST_SEQ'), 'Assassin’s Creed Origins review: A living, breathing ancient world', 'https://arstechnica.com/gaming/2017/10/assassins-creed-origins-review-a-living-breathing-ancient-world/', 20, 1, 'story', current_timestamp);
INSERT INTO POST(post_id, post_title, post_url, score, contributor_id, post_type, created_date) VALUES(nextval('POST_SEQ'), 'Portland Retro Gaming Expo delivers the industry rarest, weirdest stuff', 'https://arstechnica.com/gaming/2017/10/portland-retro-gaming-expo-delivers-the-industrys-rarest-weirdest-stuff/', 0, 1, 'story', current_timestamp);
INSERT INTO POST(post_id, post_title, post_url, score, contributor_id, post_type, created_date) VALUES(nextval('POST_SEQ'), 'Here are humanity’s best ideas on how to store energy', 'https://arstechnica.com/information-technology/2017/10/a-world-tour-of-some-of-the-biggest-energy-storage-schemes/', 30, 1, 'story', current_timestamp);
INSERT INTO POST(post_id, post_title, post_url, score, contributor_id, post_type, created_date) VALUES(nextval('POST_SEQ'), 'Danish amateur submariner admits to dismembering reporter', 'https://arstechnica.com/tech-policy/2017/10/danish-amateur-submariner-admits-to-dismembering-reporter/', 30, 1, 'story', current_timestamp);
INSERT INTO POST(post_id, post_title, post_url, score, contributor_id, post_type, created_date) VALUES(nextval('POST_SEQ'), 'Apple hears AV geeks, will give Apple TV 4K owners more settings control', 'https://arstechnica.com/gadgets/2017/10/apple-will-fix-apple-tv-4ks-hdr-issues-with-next-tvos-update/', 30, 1, 'story', current_timestamp);
INSERT INTO POST(post_id, post_title, post_url, score, contributor_id, post_type, created_date) VALUES(nextval('POST_SEQ'), 'Surface Pro with 450Mbps LTE launching December 1, starting at $1,149', 'https://arstechnica.com/gadgets/2017/10/surface-pro-with-450mbps-lte-launching-december-1-starting-at-1149/', 30, 1, 'story', current_timestamp);
INSERT INTO POST(post_id, post_title, post_url, score, contributor_id, post_type, created_date) VALUES(nextval('POST_SEQ'), 'Harman Kardon Invoke review: Cortana isn’t too comfortable in the home yet', 'https://arstechnica.com/gadgets/2017/10/harman-kardon-invoke-review-cortana-isnt-too-comfortable-in-the-home-yet/', 30, 1, 'story', current_timestamp);
INSERT INTO POST(post_id, post_title, post_url, score, contributor_id, post_type, created_date) VALUES(nextval('POST_SEQ'), 'Waymo has a big lead in driverless cars—but here’s how it could lose it', 'https://arstechnica.com/cars/2017/10/waymo-has-a-big-lead-in-driverless-cars-but-heres-how-they-could-lose-it/', 30, 1, 'story', current_timestamp);
INSERT INTO POST(post_id, post_title, post_url, score, contributor_id, post_type, created_date) VALUES(nextval('POST_SEQ'), 'Apple reportedly building iPhones, iPads without Qualcomm chips', 'https://arstechnica.com/gadgets/2017/10/on-the-outs-with-qualcomm-apple-looking-at-intel-mediatek-for-modem-silicon/', 30, 1, 'story', current_timestamp);
INSERT INTO POST(post_id, post_title, post_url, score, contributor_id, post_type, created_date) VALUES(nextval('POST_SEQ'), 'Production problems at Tesla/Panasonic Gigafactory may be at an end', 'https://arstechnica.com/cars/2017/10/production-problems-at-teslapanasonic-gigafactory-may-be-at-an-end/', 30, 1, 'story', current_timestamp);
INSERT INTO POST(post_id, post_title, post_url, score, contributor_id, post_type, created_date) VALUES(nextval('POST_SEQ'), 'Bloodhound SSC finally runs, breaks 210mph in first runway test', 'https://arstechnica.com/cars/2017/10/bloodhound-ssc-finally-runs-breaks-210mph-in-first-runway-test/', 30, 1, 'story', current_timestamp);
INSERT INTO POST(post_id, post_title, post_url, score, contributor_id, post_type, created_date) VALUES(nextval('POST_SEQ'), 'Many viruses activate a single RNA to enable successful infections', 'https://arstechnica.com/science/2017/10/many-viruses-activate-a-single-rna-to-enable-successful-infections/', 30, 1, 'story', current_timestamp);
INSERT INTO POST(post_id, post_title, post_url, score, contributor_id, post_type, created_date) VALUES(nextval('POST_SEQ'), 'The end of an era came long before the end of Cassini', 'https://arstechnica.com/science/2017/10/i-didnt-follow-cassini-carefully-but-i-still-miss-it/', 30, 1, 'story', current_timestamp);
INSERT INTO POST(post_id, post_title, post_url, score, contributor_id, post_type, created_date) VALUES(nextval('POST_SEQ'), 'Emissions, eschmissions: How to (simply) reduce your carbon footprint in 2017', 'https://arstechnica.com/science/2017/10/emissions-eschmissions-how-to-simply-reduce-your-carbon-footprint-in-2017/', 30, 1, 'story', current_timestamp);


INSERT INTO POST(post_id, post_text, score, contributor_id, parent_id, post_type, created_date) VALUES(nextval('POST_SEQ'), 'Great article!!', 10, 2, 1, 'comment', current_timestamp);
INSERT INTO POST(post_id, post_text, score, contributor_id, parent_id, post_type, created_date) VALUES(nextval('POST_SEQ'), 'Yes that is truely a great article!!', 10, 1, 1, 'comment', current_timestamp);
INSERT INTO POST(post_id, post_text, score, contributor_id, parent_id, post_type, created_date) VALUES(nextval('POST_SEQ'), 'Could not agree more.', 10, 2, 1, 'comment', current_timestamp);
INSERT INTO POST(post_id, post_text, score, contributor_id, parent_id, post_type, created_date) VALUES(nextval('POST_SEQ'), 'Very impresive work.', 10, 2, 1, 'comment', current_timestamp);
INSERT INTO POST(post_id, post_text, score, contributor_id, parent_id, post_type, created_date) VALUES(nextval('POST_SEQ'), 'Good job.', 10, 2, 1, 'comment', current_timestamp);
INSERT INTO POST(post_id, post_text, score, contributor_id, parent_id, post_type, created_date) VALUES(nextval('POST_SEQ'), 'More like this. Thank you!', 10, 2, 1, 'comment', current_timestamp);
