<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<jsp:include page="header.jsp" />
		
	<div id="main">
		<div id="message" class="row well well-sm">
			<div class="col-md-6 col-md-offset-2">
				<h4></h4>
			</div>
		</div>
		<input type="hidden" id="currentUserEmail" value="${currentUserEmail}"/>
		<div id="feedbackSection">
			<div class="row">
				<div class="col-md-6 col-md-offset-4">
					<h3 class="questionText">How was our Annual Event?</h3>
				</div>
			</div>
			<div class="row">
				<div class="col-md-8 col-md-offset-2">
					<h4>Tell Us Your Feedback using the below form. Please note that your feedback is very valuable to us and we are always looking for honest feedback to know about the things we are doing good and any areas we improve upon.</h4>
					<p><textarea id="questionResponse" class="form-control" rows="8"></textarea></p>
					<p><input class="btn  btn-success" type="button" id="submitResponse" value="Submit Feedback" /></p>
				</div>
			</div>
		</div>
		
		<div class="row" id="loader">
			<div class="col-md-4 col-md-offset-6">
				<img src="img/loader_64.gif" alt="loading data...."/>
			</div>
		</div>
		
		<c:if test="${superAdmin == true}" >
			<div class="row">
				<div class="col-md-8 col-md-offset-2">
					<p><a id="togglAdminSection" href="javascript:void(0)">Toggle Admin Section</a></p>
				</div>
			</div>
	
			
		 	
			<div id="adminSection">
				<div class="row newQuestion">
					<div class="col-md-8 col-md-offset-2">
						<h2 class="bg-primary">ADMIN SECTION</h2>
						<h3>Ask a New Question using below form</h3>
						<p>What do you want to ask to the people of your company Today?</p>
						<p><textarea id="feedback-question" class="form-control" rows="3"></textarea></p>
						<p><input class="btn btn-success" type="button" id="submitQuestion" value="Ask for Feedback"></p>
						<div class="alert alert-warning">Please note that submitting a new question will delete all the results for the previous question. Please make sure that you have already stored all results of the previous feedback question.</div>
					</div>
				</div>
		
				<div class="row previousQuestion">
					<div class="col-md-8 col-md-offset-2">
						<h2>Most Recent Question Asked</h2>
						<h3 class="questionText">How was our Annual Event?</h3>
						<p><input class="btn btn-success" type="button" id="seeResults" value="See Results"></p>
					</div>
				</div>
				<div class="row previousQuestionResults">
					<div class="col-md-8 col-md-offset-2">
						<div class="alert alert-info dataSheetLink">You can access the detailed responses of all users in the spreadsheet at the following locations : <a href="http://docs.google.com" target="_blank">See Detailed Data</a></div>
						<div class="sentimentAnalysisReport">
							<div class="row">	
								<div class="col-md-10">
									<p class="totalResponses">Total number of people that responded: <span>nil</span></p>					
									<div class="sentimentNumbers">
										<p class="numPositiveResponse">Total Positive Sentiments : <span>nil</span></p>
										<p class="numNuetralResponse">Total Neutral Sentiments : <span>nil</span></p>
										<p class="numNegativeResponse">Total Positive Sentiments : <span>nil</span></p>
										<p class="overallSentiment">Overall Sentiment of the Audience :  <span class="type"></span> (with a score* of = <span class="score"></span>)</p>
									</div>
								</div>
							</div>
							
							
							
						</div>
					</div>
				</div>
			</div>
		</c:if>
	</div>
	
	<jsp:include page="footer.jsp" />
	
	