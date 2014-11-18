// the script for this submission
var script = {
  /*
   * Initializes the script.
   */
  init : function() {
	  
	  $('#togglAdminSection').on("click", function(){
		  if ($('#adminSection').css('display') == 'none') {
			  $('#adminSection').show();
		  } else {
			  $('#adminSection').hide();
		  }
	  });
	  
	  $.get('rest/settings/configuration', function(data) {
		  var questionText = data.questionText;
		  if(questionText != '') {
			  $('.questionText').text(questionText);
			  $('#feedbackSection').show();
		  } else {
			  $('#message h4').text("Currently there is no feedback being asked for. Please check back later.");
			  $('#message').show();
		  }
		  $('#loader').hide();
	  });
	  
	  $('#submitQuestion').on("click", function() {
		  $('#message').hide();
		  var newConfiguration = {
			   questionStatus : "running",
	           questionText : $('#feedback-question').val()
          };
		  
		  $.ajax('rest/settings/save-question', {
			  data : JSON.stringify(newConfiguration),
		      type : 'POST',
		      dataType : 'text',
		      contentType : 'application/json',
		      success : function(data, response) {
		    	  $('#message h4').text("You have successfully put a new question to the audience. Page will now reload in 2 seconds to reflect new data.");
		    	  $('#message').show();
		    	  setTimeout(function(){
		    		  location.reload();
		    	  }, 2000);
		      },
		      error : function(xhr, status) {
		    	  alert("error in saving a new question.");
		      }
		  });
	  });
	  
	  
	  $('#submitResponse').on("click", function() {
		  var newResponse = {
			   fullText : $('#questionResponse').val(),
			   questionText : $('#feedbackSection .questionText').text(),
			   userEmail : $('#currentUserEmail').val()
          };
		  
		  $.ajax('rest/settings/save-response', {
			  data : JSON.stringify(newResponse),
		      type : 'POST',
		      dataType : 'text',
		      contentType : 'application/json',
		      success : function(data, response) {
		    	  $('#message h4').text("You response has been recorded. Thanks for your invaluable feedback.");
		    	  $('#message').show();
		    	  $('#questionResponse').val('');
		      },
		      error : function(xhr, status) {
		    	  alert("error in saving a new question.");
		      }
		  });
	  });
	  
	  
	  $('#toggleResults').on("click", function() {
		  if ($('.previousQuestionResults').css('display') == 'none') {
			  
			  $.get('rest/settings/get-responses', function(data) {
				  
				  $('.totalResponses span').text(data.length);
				  
				  var totalResponses = data.length;
				  var overallSentimentScore = 0;
				  var totalPositiveSentiments = 0;
				  var totalNegativeSentiments = 0
				  var totalNeutralSentiments = 0;
				  var sentiment;
				  var score;
				  
				  for (var key in data) {
					  var obj = data[key];
					  sentiment = obj.sentiment.sentiment;
			  		  score = obj.sentiment.score;
			  		  if(sentiment == 'positive') {
			  			  totalPositiveSentiments++;
			  		  } else if (sentiment == 'negative') {
			  			  totalNegativeSentiments++;
			  		  } else if(sentiment == 'neutral') {
			  			  totalNeutralSentiments++;
			  		  }
			  		  overallSentimentScore += score;
				  }
				  	$(".numPositiveResponse span").text(totalPositiveSentiments);
				  	$(".numNuetralResponse span").text(totalNeutralSentiments);
				  	$(".numNegativeResponse span").text(totalNegativeSentiments);
				  	
				  	if(overallSentimentScore > 0) {
				  		$('.overallSentiment span.type').text("POSITIVE");
				  	} else if (overallSentimentScore < 0) {
				  		$('.overallSentiment span.type').text("NEGATIVE");
				  	} else {
				  		$('.overallSentiment span.type').text("NEUTRAL");
				  	}
				  	$('.overallSentiment span.score').text(overallSentimentScore);
				  	
				  	$('.previousQuestionResults').show();
			  });
	  		} else {
			  	$('.previousQuestionResults').hide();
	  		}
	  });
	  
  }

};

/**
 * When the document is ready, initialize the script object.
 */
$(function() {
  script.init();
});