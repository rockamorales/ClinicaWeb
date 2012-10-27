/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



function FormatNumber(num,decimalNum,bolLeadingZero,bolParens,bolCommas)
/**********************************************************************
	IN:
		NUM - the number to format
		decimalNum - the number of decimal places to format the number to
		bolLeadingZero - true / false - display a leading zero for
										numbers between -1 and 1
		bolParens - true / false - use parenthesis around negative numbers
		bolCommas - put commas as number separators.

	RETVAL:
		The formatted number!

xxxxxx
 **********************************************************************/
{
        if (isNaN(parseInt(num))) return "NaN";

	var tmpNum = num;
	var iSign = num < 0 ? -1 : 1;		// Get sign of number

	// Adjust number so only the specified number of numbers after
	// the decimal point are shown.
	tmpNum *= Math.pow(10,decimalNum);
	tmpNum = Math.round(Math.abs(tmpNum))
	tmpNum /= Math.pow(10,decimalNum);
	tmpNum *= iSign;					// Readjust for sign


	// Create a string object to do our formatting on
	var tmpNumStr = new String(tmpNum);

	// See if we need to strip out the leading zero or not.
	if (!bolLeadingZero && num < 1 && num > -1 && num != 0)
		if (num > 0)
			tmpNumStr = tmpNumStr.substring(1,tmpNumStr.length);
		else
			tmpNumStr = "-" + tmpNumStr.substring(2,tmpNumStr.length);

	// See if we need to put in the commas
	if (bolCommas && (num >= 1000 || num <= -1000)) {
		var iStart = tmpNumStr.indexOf(".");
		if (iStart < 0)
			iStart = tmpNumStr.length;

		iStart -= 3;
		while (iStart >= 1) {
			tmpNumStr = tmpNumStr.substring(0,iStart) + "," + tmpNumStr.substring(iStart,tmpNumStr.length)
			iStart -= 3;
		}
	}

	// See if we need to use parenthesis
	if (bolParens && num < 0)
		tmpNumStr = "(" + tmpNumStr.substring(1,tmpNumStr.length) + ")";
        if (tmpNumStr.indexOf(".") < 0){
           tmpNumStr += "."+fillString("0",decimalNum)
        }else{
          if (tmpNumStr.substring(tmpNumStr.indexOf(".") + 1).length < 2){
            tmpNumStr += fillString("0",decimalNum - tmpNumStr.substring(tmpNumStr.indexOf(".") + 1).length)
          }
        }
	return tmpNumStr;		// Return our formatted string!
  }

    function fillString(character, num){
      string = ""
      for (i=0;i<num;i++){
        string += character
      }
      return string
    }

function IsNull(ITEM){
  if(ITEM == null || ITEM == undefined){
  return true;
  }
  return false;
}

function IsNullEmptyOrNan(ITEM){
  if(ITEM == null || ITEM == undefined || Trim(ITEM) == "" || isNaN(ITEM)){
    return true;
  }
  return false;
}

function Trim(STRING){
STRING = LTrim(STRING);
return RTrim(STRING);
}

function RTrim(STRING){
  while(STRING.charAt((STRING.length -1))==" "){
  STRING = STRING.substring(0,STRING.length-1);
  }
  return STRING;
  }


function LTrim(STRING){
  while(STRING.charAt(0)==" "){
  STRING = STRING.replace(STRING.charAt(0),"");
  }
  return STRING;
}

/*==========================================================================#
# * Function for adding a Filter to an Input Field                          #
# * @param  : [filterType  ] Type of filter 0=>Alpha, 1=>Num, 2=>AlphaNum   #
# * @param  : [evt         ] The Event Object                               #
# * @param  : [allowDecimal] To allow Decimal Point set this to true        #
# * @param  : [allowCustom ] Custom Characters that are to be allowed       #
#==========================================================================*/
function filterInput(filterType, evt, allowDecimal, allowCustom){
    var keyCode, Char, inputField, filter = '';
    var alpha = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    var num   = '0123456789';
    // Get the Key Code of the Key pressed if possible else - allow
    if(window.event){
        keyCode = window.event.keyCode;
        evt = window.event;
    }else if (evt)keyCode = evt.which;
    else{ return true; }
    // Setup the allowed Character Set
    if(filterType == 0) filter = alpha;
    else if(filterType == 1) filter = num;
    else if(filterType == 2) filter = alpha + num;
    if(allowCustom)filter += allowCustom;
    if(filter == ''){return true};
    // Get the Element that triggered the Event
    inputField = evt.srcElement ? evt.srcElement : evt.target || evt.currentTarget;
    // If the Key Pressed is a CTRL key like Esc, Enter etc - allow
    if((keyCode==null) || (keyCode==0) || (keyCode==8) || (keyCode==9) || (keyCode==13) || (keyCode==27) )return true;
    // Get the Pressed Character
    Char = String.fromCharCode(keyCode);
    // If the Character is a number - allow
    if((filter.indexOf(Char) > -1)) return true;
    // Else if Decimal Point is allowed and the Character is '.' - allow
    else if(filterType == 1 && allowDecimal && (Char == '.') && inputField.value.indexOf('.') == -1)return true;
    else return false;
}

/**
 * Funcion para adicionar filtro a los FieldText, TextArea, Calendar       
 * @param  : [filterType  ] Type of filter 0=>Alpha, 1=>Num, 2=>AlphaNum   
 * @param  : [evt         ] The Event Object                               
 * @param  : [allowDecimal] To allow Decimal Point set this to true        
 * @param  : [allowCustom ] Custom Characters that are to be allowed       
 */
function keyToUpperCase(field, evt) {
  if (document.all) {
    var c = event.keyCode;
    var C = String.fromCharCode(c).toUpperCase().charCodeAt();
    event.keyCode = C;
    return true;
  }
  else
    return true;
}
