$().ready(function () {
  $("#email").keyup(function () {
    $.get(
      "/member/regist/available",
      { email: $(this).val() },
      function (availableResponse) {
        var email = availableResponse.email;
        var available = availableResponse.available;

        if (available) {
          // 사용할 수 있는 이메일이라면?
          $("#email").addClass("available");
          $("#email").removeClass("unusable");
          $("input[type=submit]").removeAttr("disabled");
        } else {
          // 사용할 수 없는 이메일이라면?
          $("#email").addClass("unusable");
          $("#email").removeClass("available");
          $("input[type=submit]").attr("disabled", "disabled");
        }

        console.log(email);
        console.log(available);
      }
    );
  });
});
