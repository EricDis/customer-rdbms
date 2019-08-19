<?php

class DatabaseHelper
{
    // Since the connection details are constant, define them as const
    // We can refer to constants like e.g. DatabaseHelper::username
	//for the use on the almighty server of the university of vienna...
    const username = 'MATRLNO'; // use a + your matriculation number
    const password = 'PASS'; // use your oracle db password
    const con_string = 'lab';

    // Since we need only one connection object, it can be stored in a member variable.
    // $conn is set in the constructor.
    protected $conn;

    // Create connection in the constructor
    public function __construct()
    {
        try {
            // Create connection with the command oci_connect(String(username), String(password), String(connection_string))
            // The @ sign avoids the output of warnings
            // It could be helpful to use the function without the @ symbol during developing process
            $this->conn = @oci_connect(
                DatabaseHelper::username,
                DatabaseHelper::password,
                DatabaseHelper::con_string
            );

            //check if the connection object is != null
            if (!$this->conn) {
                // die(String(message)): stop PHP script and output message:
                die("DB error: Connection can't be established!");
            }

        } catch (Exception $e) {
            die("DB error: {$e->getMessage()}");
        }
    }

    // Used to clean up
    public function __destruct()
    {
        // clean up
        @oci_close($this->conn);
    }

    // This function creates and executes a SQL select statement and returns an array as the result
    // 2-dimensional array: the result array contains nested arrays (each contains the data of a single row)
    public function selectFromKundeWhere($kundenID, $nachname, $vorname)
    {
        // Define the sql statement string
        // Notice that the parameters $kundenID, $vorname, $nachname in the 'WHERE' clause
        $sql = "SELECT * FROM kunde
            WHERE kundenID LIKE '%{$kundenID}%'
              AND upper(nachname) LIKE upper('%{$nachname}%')
              AND upper(vorname) LIKE upper('%{$vorname}%')
            ORDER BY kundenID ASC";

        // oci_parse(...) prepares the Oracle statement for execution
        // notice the reference to the class variable $this->conn (set in the constructor)
        $statement = @oci_parse($this->conn, $sql);

        // Executes the statement
        @oci_execute($statement);

        // Fetches multiple rows from a query into a two-dimensional array
        // Parameters of oci_fetch_all:
        //   $statement: must be executed before
        //   $res: will hold the result after the execution of oci_fetch_all
        //   $skip: it's null because we don't need to skip rows
        //   $maxrows: it's null because we want to fetch all rows
        //   $flag: defines how the result is structured: 'by rows' or 'by columns'
        //      OCI_FETCHSTATEMENT_BY_ROW (The outer array will contain one sub-array per query row)
        //      OCI_FETCHSTATEMENT_BY_COLUMN (The outer array will contain one sub-array per query column. This is the default.)
        @oci_fetch_all($statement, $res, 0, 0, OCI_FETCHSTATEMENT_BY_ROW);

        //clean up;
        @oci_free_statement($statement);

        return $res;
    }
	
	public function selectFromArtikelWhere($artNr, $bezeichnung, $kategorie)
    {
        // Define the sql statement string
        $sql = "SELECT * FROM artikel
            WHERE artNr LIKE '%{$artNr}%'
              AND upper(bezeichnung) LIKE upper('%{$bezeichnung}%')
              AND upper(kategorie) LIKE upper('%{$kategorie}%')
            ORDER BY artNr ASC";

        // oci_parse(...) prepares the Oracle statement for execution
        // notice the reference to the class variable $this->conn (set in the constructor)
        $statement = @oci_parse($this->conn, $sql);

        // Executes the statement
        @oci_execute($statement);

        // Fetches multiple rows from a query into a two-dimensional array
        // Parameters of oci_fetch_all:
        //   $statement: must be executed before
        //   $res: will hold the result after the execution of oci_fetch_all
        //   $skip: it's null because we don't need to skip rows
        //   $maxrows: it's null because we want to fetch all rows
        //   $flag: defines how the result is structured: 'by rows' or 'by columns'
        //      OCI_FETCHSTATEMENT_BY_ROW (The outer array will contain one sub-array per query row)
        //      OCI_FETCHSTATEMENT_BY_COLUMN (The outer array will contain one sub-array per query column. This is the default.)
        @oci_fetch_all($statement, $res, 0, 0, OCI_FETCHSTATEMENT_BY_ROW);

        //clean up;
        @oci_free_statement($statement);

        return $res;
    }

    // This function creates and executes a SQL insert statement and returns true or false
    public function insertIntoKunde($nachname, $vorname, $ort, $plz, $strasse, $hausNr)
    {
        $sql = "INSERT INTO KUNDE (NACHNAME, VORNAME, ORT, PLZ, STRASSE, HAUSNR) VALUES ('{$nachname}', '{$vorname}', '{$ort}', '{$plz}', '{$strasse}', '{$hausNr}')";

        $statement = @oci_parse($this->conn, $sql);
        $success = @oci_execute($statement) && @oci_commit($this->conn);
        @oci_free_statement($statement);
        return $success;
    }

    // Using a Procedure
    // This function uses a SQL procedure to delete a person and returns an errorcode (&errorcode == 1 : OK)
    public function deleteKunde($kundenID)
    {
        // It is not necessary to assign the output variable,
        // but to be sure that the $errorcode differs after the execution of our procedure we do it anyway
        $errorcode = 0;

        // In our case the procedure P_DELETE_KUNDE takes two parameters:
        //  1. kundenID (IN parameter)
        //  2. error_code (OUT parameter)

        // The SQL string
        $sql = 'BEGIN P_DELETE_KUNDE(:kundenID, :errorcode); END;';
        $statement = @oci_parse($this->conn, $sql);

        //  Bind the parameters
        @oci_bind_by_name($statement, ':kundenID', $kundenID);
        @oci_bind_by_name($statement, ':errorcode', $errorcode);

        // Execute Statement
        @oci_execute($statement);

        //Note: Since we execute COMMIT in our procedure, we don't need to commit it here.
        //@oci_commit($statement); //not necessary

        //Clean Up
        @oci_free_statement($statement);

        //$errorcode == 1 => success
        //$errorcode != 1 => Oracle SQL related errorcode;
        return $errorcode;
    }

    // NOT IN USE - ALTERNATIVE to a simple insert (method return person_id)
    // using a Procedure to add a Person -> the Id of the currently added Person is return (otherwise false)
    public function addKunde($nachname, $vorname, $ort, $plz, $strasse, $hausNr)
    {
        $kundenId = -1;
        $sql = 'BEGIN P_ADD_KUNDE(:nachname, :vorname, :ort, :plz, :strasse, :hausNr, :kundenID); END;';
        $statement = @oci_parse($this->conn, $sql);

        @oci_bind_by_name($statement, ':nachname', $nachname);
        @oci_bind_by_name($statement, ':vorname', $vorname);
		@oci_bind_by_name($statement, ':ort', $ort);
		@oci_bind_by_name($statement, ':plz', $plz);
		@oci_bind_by_name($statement, ':strasse', $strasse);
		@oci_bind_by_name($statement, ':hausNr', $hausNr);
		@oci_bind_by_name($statement, ':kundenID', $kundenID);

        if (!@oci_execute($statement)) {
            @oci_commit($this->conn);
        }
        @oci_free_statement($statement);

        return $kundenID;
    }
}
