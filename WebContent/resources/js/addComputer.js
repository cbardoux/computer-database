/**
 * 
 */

 function limitMinDate(value) {
   var input = document.getElementsByName("discontinued");
   input[0].setAttribute("min", value);
}

 function limitMaxDate(value) {
   var input = document.getElementsByName("introduced");
   input[0].setAttribute("max", value);
}