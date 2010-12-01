<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="banner.jsp" />
<!--<jsp:include page="error.jsp" />-->
<link rel="stylesheet" href="style/campaign.css" type="text/css" />

<style type="text/css">
a:link {color:#FFFFFF;}    /* unvisited link */
a:visited {color:#FFFFFF;} /* visited link */
a:hover {color:yellow;font-weight: bold}   /* mouse over link */
a:active {color:#FFFFFF;}  /* selected link */
</style>

<script type="text/javascript"
	src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script type="text/javascript"
	src="http://google-maps-utility-library-v3.googlecode.com/svn/tags/markermanager/1.0/src/markermanager.js"></script>
<script type="text/javascript">
  function initialize() {
	var latLng = new Array();
 	var markersArray = [];
	var centerLatLng = new google.maps.LatLng(0,0);
	/**
	 * The MarkerManager object.
	 * @type {MarkerManager}
	 */
	var mgr = null;
	 
	var myOptions = {
		      zoom: 1,
		      center: centerLatLng,
		      mapTypeId: google.maps.MapTypeId.ROADMAP
		    };
    var map = new google.maps.Map(document.getElementById("map_canvas"),
        myOptions);
    
    <c:if test="${!(empty latLngArray)}">
	<c:forEach var="entry" items="${latLngArray}" varStatus="status">			 
	 	latLng[${status.index}] = new google.maps.LatLng(${entry[1]}, ${entry[0]});
	 	marker = new google.maps.Marker({
	 	      position: latLng[${status.index}]
	 	});
	 	markersArray.push(marker);
	</c:forEach>
	</c:if>  
	mgr = new MarkerManager(map, {trackMarkers: true, maxZoom: 15});
	google.maps.event.addListener(mgr, 'loaded', function() {
	      mgr.addMarkers(markersArray[0], 0, 5);
	      mgr.addMarkers(markersArray, 6, 11);
	      mgr.refresh();
	    });

  }
</script>
  <div id="map_canvas" style="width:100%; height:70%"></div>
<div ><br /></div>


<script>
function validate(){
	var newKey = document.getElementById('newKeyword').value;
	if(newKey==null||newKey==""){
		alert('Enter a keyword!');
		return false;
	}
	

    // Check for white space
    var spaceChar = " ";
    
    if (newKey.indexOf(spaceChar)>-1){
	    alert("Spaces not allowed!");
        return false;
    }
 
    //Check for special characters
    var iChars = "!@#$%^&*()+=-[]\\\';,./{}|\":<>?";

  	for (var i = 0; i < newKey.length; i++) {
  		if (iChars.indexOf(newKey.charAt(i)) != -1) {
  			alert ("Special charaters not allowed!");
  			return false;
  		}
  	}
    
return true;
}
</script>
<div class="wordswarm" >
<div class="addKform" >
<form id="addKeywordForm" method="POST" onsubmit="return validate();"
			action="viewCampaign.do" ><input type="text" id="newKeyword"
			name="newKeyword" /> <input type="submit" id="button"
			name="button" style="width:120px; height:auto;
			font-size:18px" value="Add Keyword" /></form>
</div>		
<br />
<!--<div style="border:1px solid white"><br />-->
<!--<h2>Tracking...</h2>-->
<!--</div>-->
<c:if test="${(empty trackWords)}">
	<div >Nothing</div>

</c:if> 
<c:if test="${!(empty trackWords)}">
	<c:forEach var="entry" items="${trackWords}">
<!--<div style="border:1px solid white;max-width:50px">-->
		
		<form id="${entry.keywordId}" method="POST" action="home.do" style="border:1px solid white;display:none"><input
			type="hidden" id="${entry.keywordId}hf" name="keywordId" value="${entry.keywordDesc}" />
		</form>
		<input type="checkbox" id="${entry.keywordId}check" name="${entry.keywordId}name" value="${entry.keywordDesc}" style="width:2em" checked><font style="font-size:1em">${entry.keywordDesc}<br />
<!--		<a href="#" style="font-size:${entry.fontSize}"-->
<!--			onClick="document.getElementById('${entry.keywordId}').submit()">${entry.keywordDesc}</a>-->
		 
		

<!--		</div>-->
	</c:forEach>
</c:if>
<div ></div>
</div>
<div ><br /></div>

<div id="recentTweets">
<script>
var searchStr='';
<c:forEach var="entry" items="${trackWords}">
if(searchStr.length==0){
	 searchStr = document.getElementById('${entry.keywordId}hf').value;
}
else{
	 searchStr = searchStr + ' OR ' + document.getElementById('${entry.keywordId}hf').value;
}
</c:forEach>
</script>
<script src="http://widgets.twimg.com/j/2/widget.js"></script>
<script>
new TWTR.Widget({
  version: 2,
  type: 'search',
  search: searchStr,
  interval: 6000,
  title: '',
  subject: '',
  width: 'auto',
  height: 300,
  theme: {
    shell: {
      background: '#8ec1da',
      color: '#ffffff'
    },
    tweets: {
      background: '#ffffff',
      color: '#444444',
      links: '#1985b5'
    }
  },
  features: {
    scrollbar: false,
    loop: true,
    live: true,
    hashtags: true,
    timestamp: true,
    avatars: true,
    toptweets: true,
    behavior: 'default'
  }
}).render().start();
</script>
</div>
<script>
initialize();
</script>
<jsp:include page="footer.jsp" />
