'use strict';

/**
 * Native functions
 * @author Bendy Zhang <zb@bndy.net>
 * @copyright BNDY.NET 2017
 * @see {@link http://bndy.net|Home Page}
 */


//===================================================================
/**
 * @external Object
 */

/**
 * To do something if the object hasOwnProperty.
 * @function external:Object#ifHasProperty
 * @param {string} propertyName - The property name.
 * @param {function} callback - The callback function.
 * @example
 * {name: 'Bendy'}.ifHasProperty('name', function(propertyValue) { });
 */

Object.defineProperty(Object.prototype, 'ifHasProperty', {
    value: function (propertyName, callback) {
        if (this.hasOwnProperty(propertyName)) {
            if (callback) {
                callback(this[propertyName]);
            }
        }
    },
    enumerable: false
});

//===================================================================
/**
 * @external String
 */

if (!String.prototype.trim) {
    /**
     * Removes whitespace from both ends of a string. Whitespace in this context is all the whitespace characters (space, tab, no-break space, etc.) and all the line terminator characters (LF, CR, etc.).
     * @function external:String#trim
     * @returns {string} - A new string representing the calling string stripped of whitespace from both ends.
     * @example
     * ' bendy '.trim();
     * // => bendy
     */
    String.prototype.trim = function () {
        return this.replace(/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, '');
    };
}

/**
 * Removes whitespace from the left of a string.
 * @function external:String#ltrim
 * @returns {string}
 * @example
 * ' bendy '.ltrim();
 * // => bendy  
 */
String.prototype.ltrim = function () {
    if (String.prototype.trimLeft) {
        return this.trimLeft();
    }

    return this.replace(/^\s+/, '');
}
/**
 * Removes whitespace from the right of a string.
 * @function external:String#rtrim
 * @returns {string}
 * @example
 * ' bendy '.rtrim();
 * // =>   bendy
 */
String.prototype.rtrim = function () {
    if (String.prototype.trimRight) {
        return this.trimRight();
    }

    return this.replace(/\s+$/, '');
}
/**
 * Replaces text inside a string.
 * @function external:String#replaceAll
 * @returns {string}
 * @example
 * 'Bendy Zhang'.replaceAll('n', '-');
 * // => Be-dy Zha-g
 */
String.prototype.replaceAll = function (search, replace) {
    if (replace === undefined) {
        return this.toString();
    }
    return this.split(search).join(replace);
};

/**
 * Dasherizes string.
 * @function external:String#dasherize
 * @returns {string}
 * @example
 * 'Bendy Zhang 1949.10'.dasherize();
 * // => bendy-zhang-1949-10
 */
String.prototype.dasherize = function () {
    return this.replace(/\W+/g, "-").toLowerCase();
};

/**
 * Capitalizes string.
 * @function external:String#capitalize
 * @returns {string}
 * @example
 * 'bendy zhang'.capitalize();
 * // => Bendy Zhang
 */
String.prototype.capitalize = function () {
    var tmp = this.split(' ');
    var result = '';
    for (var idx = 0; idx < tmp.length; idx++) {
        var s = tmp[idx];
        if (s) {
            result += (idx ? ' ' : '') + s.charAt(0).toUpperCase() + s.slice(1);
        } else {
            result += (idx ? ' ' : '');
        }
    }
    return result;
};