// This is a manifest file that'll be compiled into application.js, which will include all the files
// listed below.
//
// Any JavaScript/Coffee file within this directory, lib/assets/javascripts, vendor/assets/javascripts,
// or vendor/assets/javascripts of plugins, if any, can be referenced here using a relative path.
//
// It's not advisable to add code directly here, but if you do, it'll appear at the bottom of the
// the compiled file.
//
// WARNING: THE FIRST BLANK LINE MARKS THE END OF WHAT'S TO BE PROCESSED, ANY BLANK LINE SHOULD
// GO AFTER THE REQUIRES BELOW.
//

//= require jquery
//= require jquery_ujs
//= require jquery-ui
//= require_tree .

$(document).ready(function OpenHelp() {
    var triggers = $(".modalInput").overlay({

      // some mask tweaks suitable for modal dialogs
      mask: {
        color: '#ebecff',
        loadSpeed: 200,
        opacity: 0.6
      },

      closeOnClick: false
  });

  var buttons = $("#yesno button").click(function(e) {

      // get user input
      var yes = buttons.index(this) === 0;
  });
});


//http://railscasts.com/episodes/196-nested-model-form-part-1
//http://railscasts.com/episodes/197-nested-model-form-part-2
function remove_fields (link) {
	$(link).prev("input[type=hidden]").val("1");
	$(link).closest(".question_details").hide();
}

function add_fields(link, association, content) {
  var new_id = new Date().getTime();
  var regexp = new RegExp("new_" + association, "g")
  $(link).parent().before(content.replace(regexp, new_id))
}