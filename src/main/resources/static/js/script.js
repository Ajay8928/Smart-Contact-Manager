const togglesidebar = ()=>
{
    
    if($(".sidebar").is(":visible"))
    {
       $(".sidebar").css("display","none");
       $(".content").css("margin-left","10px");
    }
    else
    {
        $(".sidebar").css("display","block");
       $(".content").css("margin-left","20%");

    }
    
};