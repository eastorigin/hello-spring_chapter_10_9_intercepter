$().ready(function () {
  var pathname = location.pathname;
  var search = location.search;
  var nextUrl = "";

  if (pathname === "/member/login") {
    nextUrl = "/board/list";
  } else {
    nextUrl = pathname + search;
  }

  $("input[name=nextUrl]").val(nextUrl);
});
