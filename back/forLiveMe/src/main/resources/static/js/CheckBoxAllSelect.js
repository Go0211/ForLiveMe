$("#joinForm #checkAll").on("click", function () {
    if($("#checkAll").prop("checked")){
        $("input[type=checkbox]").prop("checked", true);
    } else {
        $("input[type=checkbox]").prop("checked", false);
    }
});

// $("#joinForm").on('click', 'input:not(#checkAll)', function () {
//     let is_checked = true;
//     $(".checkbox_group input:not(#checkAll)").each(function() {
//         is_checked =  is_checked && $(this).is(":checked");
//     })
//     $("#checkAll").prop("checked", is_checked)
// });