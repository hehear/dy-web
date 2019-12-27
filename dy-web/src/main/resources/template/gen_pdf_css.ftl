/*
 * css style of notice generator
 *
 * @auther dxy
 *
 */
 
@page {  
	size: 8.5in 11in;
	margin: 16mm 16mm;
	/*margin: 17.65mm 16.94mm;*/
}

.no-wrap {
	word-break:keep-all;
	white-space:nowrap;
}

.bold {
	font-weight:bold;
}

.note {
	font-size:0.75em;
	margin-left:3em;
}

p {
	margin: 6px 0
}

table {
	width:100%;
	border-collapse:collapse;
	border:black solid 1px;
}

.tableVerticalMargin {
	line-height:6px; /* 表格前后距离，使用p实现 */
}
   
td, th {
	border:black solid 0.2px;
	min-height: 25px;
	word-wrap:break-word;
	word-break:break-all;
}

.index-min-width {
	min-width:30px;
}

table th {
	text-align:left;
	font-weight:bold;
	font-size:0.9167em;
}

table td {
	font-size:0.75em;
}

table thead {
	text-align:center;
}

table tbody {
	text-align:center;
}

div.newLine {
	page-break-before: always;
}

.clearFloat {
	clear:both;
}


