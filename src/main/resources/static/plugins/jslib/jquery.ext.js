'use strict';

/**
 * The jQuery plugins.
 * @author Bendy Zhang <zb@bndy.net>
 * @copyright BNDY.NET 2017
 * @see {@link http://bndy.net|Home Page}
 */

$.extend({
    /**
     * @external jQuery
     */

    /**
     * Enables/disables window scrolling.
     * @function external:jQuery.toggleScroll
     * @example
     * $.toggleScroll();
     */
    toggleScroll: function() {
        $('body').toggleScroll();
    },
});

(function ($) {
    /**
     * @external "jQuery.fn"
     */

    /**
     * Highlights the text using HTML mark.
     * @function external:"jQuery.fn"#highlightText
     * @param {string} text - The text needs to highlight.
     * @returns {jQuery} The elements matched.
     * @example
     *  $('p').highlightText('text');
     */
    $.fn.highlightText = function (text) {
        $(this).each(function() {
            if ($(this).children().length === 0) {
                $(this).html($(this).text().replace(
                    new RegExp('(' + text + ')', "ig"),
                    '<mark class="highlight">$1</mark>'
                ));
            }
        });
        return $(this);
    }

    /**
     * Enables/disables element scrolling.
     * @function external:"jQuery.fn"#toggleScroll
     * @example
     * $('.box').toggleScroll();
     */
    $.fn.toggleScroll = function() {
        if ($(this).css('overflow') === 'hidden') {
            $(this).css('overflow', 'auto');
        } else {
            $(this).css('overflow', 'hidden');
        }
    }
})(jQuery);
