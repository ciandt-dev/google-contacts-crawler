<!DOCTYPE>
<html>
<head>
	
	<!-- JavaScript -->
    
    <script type="text/JavaScript" src="complements/jquery/jquery-2.1.3.min.js"></script>
    <script src="scripts/app.js"></script>
    <script src="scripts/main.js"></script>
    
    <!-- End JavaScript -->
	
	<!-- CSS -->
	<link rel="stylesheet" type="text/css" href="stylesheets/main.css" />
	<!-- End CSS -->
	
	<!-- Font's -->
	<link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
	<!-- End Font's -->
	
	<!-- Icons Font's -->
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
	<!-- End Icons Font's -->
	
	<!-- Libraries Bootstrap -->
	
	<!-- CSS -->
	<link rel="stylesheet" type="text/css" href="complements/bootstrap-3.4/css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="complements/bootstrap-3.4/css/bootstrap-theme.css" />
	
	<!-- JavaScript -->
	<script type="text/JavaScript" src="complements/bootstrap-3.4/js/bootstrap.min.js"></script>
	<!-- End Bootstrap -->
    <title>Google Contacts Crawler</title>
</head>
<body>
	<div class="container-fluid">
        <div class="row">
            <div class="col-xs-1 col-sm-1 col-md-3 col-lg-4"></div>
        <div class="style-box col-xs-10 col-sm-10 col-md-6 col-lg-4">
            <div class="title">
                <span><img class="icon" src="img/groups.png" /><label class="title"><span class="ini-title">CI&T </span>Contacts Crawler</label></span>
            </div>
            <div class="text">
                <span><label class="title-text">Authorization Agreement</label></span>
                <br />
                <span>
                    <label class="center-text text-required">
                        All your contacts names and email addresses will be imported. 
                        A contact is any address you've sent or replied an email to.
                        In order to check your contacts <a href="https://contacts.google.com/" target="_blank">click here.</a>
                        <br /><br />
                        Please make sure you are aware of this action.<br />
                        You can <a href="https://support.google.com/a/answer/2537800" target="_blank">view your security settings and revoke access</a> at anytime. 
                     </label>
                 </span>
                <span><label class="text-required"><strong>Make sure you log-in with your @ciandt.com account!</strong></label></span>
            </div>
            <div class="button-accept">
                <button id="authorize-button" class="btn btn-sm btn-success"><i class="text-button fa fa-check-square-o fa-lg"></i>Agree</button>
                <button class="btn btn-sm btn-danger"><i class="text-button fa fa-times fa-lg"></i>Disagree</button>
            </div>
            <div class="footer">
                <span><img class="icon-ciandt" src="img/ciandt.png"/></span><span class="aling-text-footer"><label class="text-footer">- All Rights Reserved CI&T</label></span>
            </div>
        </div>
        <div class="col-xs-1 col-sm-1 col-md-4 col-lg-4"></div>
        </div>
    </div>
    <!-- Modal Authorization Success-->
	<div id="authorizationsuccess" class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <label class="text-success modal-title" id="myModalLabel">Authorization Success</label>
	      </div>
	      <div class="text-default modal-body">
	        <i class="text-button fa fa-check-square-o fa-lg"></i>Approved authorization request
	      </div>
	      <div class="modal-footer">
	      </div>
	    </div>
	  </div>
    </div>
    <!-- Modal Authorization denied-->
    <div id="authorizationdenied" class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <label class="text-dined modal-title" id="myModalLabel">Authorization denied</label>
          </div>
          <div class="text-default modal-body">
               <i class="text-button fa fa-times fa-lg"></i>Authorization request denied
          </div>
          <div class="modal-footer">
          </div>
        </div>
      </div>
    </div>
</html>