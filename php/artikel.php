<?php

// ----------------------------------------------------------------------------------------------------
// - Display Errors
// ----------------------------------------------------------------------------------------------------
ini_set('display_errors', 'On');
ini_set('html_errors', 0);

// ----------------------------------------------------------------------------------------------------
// - Error Reporting
// ----------------------------------------------------------------------------------------------------
error_reporting(-1);

// ----------------------------------------------------------------------------------------------------
// - Shutdown Handler
// ----------------------------------------------------------------------------------------------------
function ShutdownHandler()
{
    if(@is_array($error = @error_get_last()))
    {
        return(@call_user_func_array('ErrorHandler', $error));
    };

    return(TRUE);
};

register_shutdown_function('ShutdownHandler');

// ----------------------------------------------------------------------------------------------------
// - Error Handler
// ----------------------------------------------------------------------------------------------------
function ErrorHandler($type, $message, $file, $line)
{
    $_ERRORS = Array(
        0x0001 => 'E_ERROR',
        0x0002 => 'E_WARNING',
        0x0004 => 'E_PARSE',
        0x0008 => 'E_NOTICE',
        0x0010 => 'E_CORE_ERROR',
        0x0020 => 'E_CORE_WARNING',
        0x0040 => 'E_COMPILE_ERROR',
        0x0080 => 'E_COMPILE_WARNING',
        0x0100 => 'E_USER_ERROR',
        0x0200 => 'E_USER_WARNING',
        0x0400 => 'E_USER_NOTICE',
        0x0800 => 'E_STRICT',
        0x1000 => 'E_RECOVERABLE_ERROR',
        0x2000 => 'E_DEPRECATED',
        0x4000 => 'E_USER_DEPRECATED'
    );

    if(!@is_string($name = @array_search($type, @array_flip($_ERRORS))))
    {
        $name = 'E_UNKNOWN';
    };

    return(print(@sprintf("%s Error in file \xBB%s\xAB at line %d: %s\n", $name, @basename($file), $line, $message)));
};

$old_error_handler = set_error_handler("ErrorHandler");

//***********actual php client code starts here....*************
//var_dump(123); testcode for manual debugging

// Include DatabaseHelper.php file
require_once('DatabaseHelper.php');

// Instantiate DatabaseHelper class
$database = new DatabaseHelper();

// Get parameters from GET Request
// Btw. you can see the parameters in the URL if they are set
$artNr = '';
if (isset($_GET['artNr'])) {
    $artNr = $_GET['artNr'];
}

$bezeichnung = '';
if (isset($_GET['bezeichnung'])) {
    $bezeichnung = $_GET['bezeichnung'];
}

$preis = '';
if (isset($_GET['preis'])) {
    $preis = $_GET['preis'];
}

$kategorie = '';
if (isset($_GET['kategorie'])) {
    $kategorie = $_GET['kategorie'];
}


//Fetch data from database
$artikel_array = $database->selectFromArtikelWhere($artNr, $bezeichnung, $kategorie);
?>

<html>
<head>
    <title>DBS Projekt-Webshop</title>
<style>
	body {background-color: grey;}
	h1 {
		font-weight: bold;
		text-decoration: underline;
	}
</style>
</head>

<body>
<br>
<h1>PHP Client for Webshop</h1>
<a href="index.php">
    go back
</a>
<!-- Search form -->
<h2>Artikel Suche:</h2>
<form method="get">
    <!-- artnr textbox:-->
    <div>
        <label for="artNr">ArtikelNr:</label>
        <input id="artNr" name="artNr" type="number" value='<?php echo $artNr; ?>' min="0">
    </div>
    <br>

    <!-- bezeichnung textbox:-->
    <div>
        <label for="bezeichnung">Bezeichnung:</label>
        <input id="bezeichnung" name="bezeichnung" type="text" class="form-control input-md" value='<?php echo $bezeichnung; ?>'
               maxlength="20">
    </div>
    <br>

    <!-- kategorie textbox:-->
    <div>
        <label for="kategorie">Kategorie:</label>
        <input id="kategorie" name="kategorie" type="text"
               value='<?php echo $kategorie; ?>' maxlength="20">
    </div>
    <br>

    <!-- Submit button -->
    <div>
        <button id='submit' type='submit'>
            Suche
        </button>
    </div>
</form>
<br>
<hr>

<!-- Search result -->
<h2>Artikel Search Result:</h2>
<table>
    <tr>
        <th>ArtNr</th>
        <th>Bezeichnung</th>
        <th>Preis</th>
		<th>Kategorie</th>
    </tr>
    <?php foreach ($artikel_array as $artikel) : ?>
        <tr>
            <td><?php echo $artikel['ARTNR']; ?>  </td>
            <td><?php echo $artikel['BEZEICHNUNG']; ?>  </td>
            <td><?php echo $artikel['PREIS']; ?>  </td>
			<td><?php echo $artikel['KATEGORIE']; ?>  </td>
        </tr>
    <?php endforeach; ?>
</table>

</body>
</html>