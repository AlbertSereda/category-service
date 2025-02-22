CREATE TABLE attribute_group
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

COMMENT ON TABLE attribute_group IS 'Attribute group information';
COMMENT ON COLUMN attribute_group.id IS 'Unique identifier for the attribute group';
COMMENT ON COLUMN attribute_group.name IS 'Name of the attribute group';

CREATE TYPE data_type AS ENUM ('STRING', 'BOOLEAN', 'NUMBER');

CREATE TABLE attribute
(
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    data_type     data_type    NOT NULL,
    description   VARCHAR(255),
    group_id      BIGINT,
    creation_date TIMESTAMP    NOT NULL DEFAULT NOW(),
    update_date   TIMESTAMP    NOT NULL DEFAULT NOW(),
    is_archive    BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT group_id_fk FOREIGN KEY (group_id) REFERENCES attribute_group (id)
);

COMMENT ON TABLE attribute IS 'Attribute information';
COMMENT ON COLUMN attribute.id IS 'Unique identifier for the attribute';
COMMENT ON COLUMN attribute.name IS 'Name of the attribute';
COMMENT ON COLUMN attribute.data_type IS 'Type of data the attribute';
COMMENT ON COLUMN attribute.description IS 'Description of the attribute';
COMMENT ON COLUMN attribute.group_id IS 'Identifier of the attribute group';
COMMENT ON COLUMN attribute.creation_date IS 'Date of creation';
COMMENT ON COLUMN attribute.update_date IS 'Date of the last update';
COMMENT ON COLUMN attribute.is_archive IS 'Archive status of the attribute';

CREATE TABLE category
(
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    description   VARCHAR(255),
    parent_id     BIGINT,
    creation_date TIMESTAMP    NOT NULL DEFAULT NOW(),
    update_date   TIMESTAMP    NOT NULL DEFAULT NOW(),
    is_archive    BOOLEAN      NOT NULL DEFAULT FALSE
);

COMMENT ON TABLE category IS 'Category information';
COMMENT ON COLUMN category.id IS 'Unique identifier for the category';
COMMENT ON COLUMN category.name IS 'Name of the category';
COMMENT ON COLUMN category.description IS 'Description of the category';
COMMENT ON COLUMN category.parent_id IS 'Parent category ID (for hierarchical structure)';
COMMENT ON COLUMN category.creation_date IS 'Date of creation';
COMMENT ON COLUMN category.update_date IS 'Date of the last update';
COMMENT ON COLUMN category.is_archive IS 'Archive status of the category';

CREATE TABLE category_attribute_link
(
    category_id  BIGINT NOT NULL,
    attribute_id BIGINT NOT NULL,
    PRIMARY KEY (category_id, attribute_id),
    CONSTRAINT category_id_fk FOREIGN KEY (category_id) REFERENCES category (id),
    CONSTRAINT attribute_id_fk FOREIGN KEY (attribute_id) REFERENCES attribute (id)
);

COMMENT ON TABLE category_attribute_link IS 'Links categories to attributes';
COMMENT ON COLUMN category_attribute_link.category_id IS 'Identifier of the category';
COMMENT ON COLUMN category_attribute_link.attribute_id IS 'Identifier of the attribute';
