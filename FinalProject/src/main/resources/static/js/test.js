$(document).ready(function () {
    var dataUser = {};
    $.get("http://"+window.location.host+"/api/user/"+$(".user-name").text(), function(data, status){
        if(data.data != null){
            dataUser.id = data.data.userId;
            dataUser.createdDate = data.data.createdDate
            dataUser.avatar = data.data.avatar
            $('#username').val(data.data.username)
            $('#fullname').val(data.data.name)
            $('#address').val(data.data.address)
            $('#email').val(data.data.email)
            $('#preview-product-img').attr('src',data.data.avatar)
            switch (data.data.gender){
                case "Male":
                    document.getElementById("male").checked = true;
                    break;
                case "Female":
                    document.getElementById("female").checked = true;
                    break;
                case "Other":
                    document.getElementById("other").checked = true;
                    break;
            }


        }
    })

    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function(e) {
                $('#preview-product-img').attr('src', e.target.result);
            }
            reader.readAsDataURL(input.files[0]);
        }
    }

    $("#input-select-img-product").change(function() {
        readURL(this);
        var formData = new FormData();
        NProgress.start();
        formData.append('file', $("#input-select-img-product")[0].files[0]);
        axios.post("/api/upload/upload-image", formData).then(function(res){
            NProgress.done();
            if(res.data.success) {
                $('#preview-product-img').attr('src', res.data.link);
                dataUser.imageurl =  res.data.link
            }
        }, function(err){
            NProgress.done();
        })
    });

    $(".btn-save-user").on("click", function () {
        if($("#username").val() === "" || $("#fullname").val() === "" || dataUser.imageurl === undefined || $("#address").val() === ""||$("#email").val() === ""
        ) {
            swal(
                'Error',
                'You need to fill all values',
                'error'
            );
            return;
        }

        dataUser.username = $('#username').val()
        dataUser.name = $('#fullname').val()
        dataUser.email = $('#email').val()
        dataUser.address = $('#address').val()
        dataUser.updatedDate = new Date().toJSON()
        dataUser.oldpassword = $('#old-password').val()
        dataUser.password = $('#new-password').val()
        if(document.getElementById("male").checked){
            dataUser.gender = "Male"
        }
        if(document.getElementById("female").checked){
            dataUser.gender = "Female"
        }
        if(document.getElementById("other").checked){
            dataUser.gender = "Other"
        }


        NProgress.start();

        var linkPost = "/api/user/update-user/" + dataUser.id;

        axios.post(linkPost, dataUser).then(function(res){
            NProgress.done();
            if(res.data.success) {
                swal(
                    'Good job!',
                    res.data.message,
                    'success'
                ).then(function() {
                    // location.reload();
                    window.location.replace('http://localhost:8080/logout');
                });
            } else {
                swal(
                    'Error',
                    res.data.message,
                    'error'
                );
            }
        }, function(err){
            NProgress.done();
            swal(
                'Error',
                'Some error when saving user',
                'error'
            )
        })
    })
})