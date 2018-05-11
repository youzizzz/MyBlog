$(function() {
	$("[data-toggle='tooltip']").tooltip();
	$("#page li").each(function() {
		if ($(this).text() == $("#hidpage").val()) {
			$(this).addClass("active");
		}
	});
	$('form').bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			username : {
				message : '用户名验证失败',
				validators : {
					notEmpty : {
						message : '用户名不能为空'
					},
					stringLength : {
						min : 6,
						max : 15,
						message : '用户名长度必须在6到18位之间'
					},
					regexp : {
						regexp : /^[a-zA-Z0-9_]+$/,
						message : '用户名只能包含大写、小写、数字和下划线'
					}
				}
			},
			password : {
				message : '密码验证失败',
				validators : {
					notEmpty : {
						message : '密码不能为空'
					},
					stringLength : {
						min : 6,
						max : 15,
						message : '密码长度至少为6位字母或数字'
					},
					regexp : {
						regexp : /^[a-zA-Z0-9_]+$/,
						message : '用户名只能包含大写、小写、数字和下划线'
					}
				}
			},
			repassword : {
				message : '密码验证失败',
				validators : {
					notEmpty : {
						message : '密码不能为空'
					},
					stringLength : {
						min : 6,
						max : 15,
						message : '密码长度至少为6位字母或数字'
					},
					regexp : {
						regexp : /^[a-zA-Z0-9_]+$/,
						message : '用户名只能包含大写、小写、数字和下划线'
					},
					identical : {
						field : 'password',
						message : '两次输入的密码不相符'
					}
				}
			},
			email : {
				validators : {
					notEmpty : {
						message : '邮箱不能为空'
					},
					emailAddress : {
						message : '邮箱地址格式有误'
					}
				}
			}
		}
	});

	$("#register input[name=username]").blur(
			function() {
				$.get('/MyBlog/register/validate', {
					"username" : $(this).val()
				}, function(data) {

					// 存在
					if (data == "success") {
						$("#register div:eq(1) i:eq(0)").removeClass(
								"glyphicon-ok").addClass("glyphicon-remove");
						$("#register div:eq(1)").removeClass("has-success")
								.addClass("has-error");
						$("#register div:eq(1) small:eq(0)").html("用户名已存在")
								.show().css("color", "red");
					}

				});
			});

	$("#register").submit(function() {
		$.post("/MyBlog/register/save", {
			"userName" : $("#register input[name=username]").val(),
			"password" : $("#register input[name=password]").val(),
			"email" : $("#register input[name=email]").val()
		}, function(data) {
			if (data == "success") {
				$("#register").modal("hide");
				
				window.location.reload();
			}
		});
		return false;
	});

	$("#loginfo").submit(
			function() {
				$.post("/MyBlog/login", {
					"userName" : $("#loginfo input[name=username]").val(),
					"password" : $("#loginfo input[name=password]").val()
				}, function(data) {
					if (data === "success") {
						$("#loginfo").modal("hide");
						window.location.reload();
					} else {
						$("#loginfo div:eq(1) i:eq(0)").removeClass(
								"glyphicon-ok").addClass("glyphicon-remove");
						$("#loginfo div:eq(1)").removeClass("has-success")
								.addClass("has-error");
						if (data == "countError") {
							$("#loginfo div:eq(1) small:eq(0)").html(
									"输入密码错误超过五次,账号已锁定").show().css("color",
									"red");
						} else {
							$("#loginfo div:eq(1) small:eq(0)").html("输入密码有误")
									.show().css("color", "red");
						}
					}
				});

				return false;
			});
});
function ArtcleTar(obj) {
	window.open($(obj).attr("href"));
}