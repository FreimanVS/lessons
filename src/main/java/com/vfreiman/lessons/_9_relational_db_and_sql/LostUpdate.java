package com.vfreiman.lessons._9_relational_db_and_sql;

/**
 * Two sessions read the same record, both update it and commit, but the result will be only the last commit.
 * It is fixed in Serializable, Repeatable read, Read commited(default) and Read uncommited.
 */
public class LostUpdate {
}
