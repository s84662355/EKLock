
 
<script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js">
</script>
 

<script>
        $.ajax({
            type:"GET",
            url:"/api",
          
            success:function(data){
                  alert(data);
            },
           
        });
 
</script>