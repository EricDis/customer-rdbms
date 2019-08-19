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
//*************actual code for addKunde starts here....****************

//include DatabaseHelper.php file
require_once('DatabaseHelper.php');

//instantiate DatabaseHelper class
$database = new DatabaseHelper();

//Grab variables from POST request
$nachname = '';
if(isset($_POST['nachname'])){
    $nachname = $_POST['nachname'];
}

$vorname = '';
if(isset($_POST['vorname'])){
    $vorname = $_POST['vorname'];
}

$ort = '';
if(isset($_POST['ort'])){
    $ort = $_POST['ort'];
}

$plz = '';
if(isset($_POST['plz'])){
    $plz = $_POST['plz'];
}

$strasse = '';
if(isset($_POST['strasse'])){
    $strasse = $_POST['strasse'];
}

$hausNr = '';
if(isset($_POST['hausNr'])){
    $hausNr = $_POST['hausNr'];
}

// Insert method
$success = $database->insertIntoKunde($nachname, $vorname, $ort, $plz, $strasse, $hausNr);

// Check result
if ($success){
    echo "Kunde '{$nachname} {$vorname}' successfully added!'";
}
else{
    echo "Error can't insert Kunde '{$nachname} {$vorname}'!";
}
?>

<!-- link back to index page-->
<br>
<a href="index.php">
    go back
</a>