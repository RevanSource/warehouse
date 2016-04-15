ALTER TABLE promotion
    ADD COLUMN store_id bigint;

ALTER TABLE promotion
    ADD CONSTRAINT fk_promotion_store_id FOREIGN KEY (store_id) REFERENCES store (id);
