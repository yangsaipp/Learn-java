public
 
class
 
VolatileTest
 {

    
int
 i = 
0
;
    
volatile
 
boolean
 flag = 
false
;

    
//Thread A
    
public
 
void
 write(){
        i = 
2
;              
//1
        flag = 
true
;        
//2
    }

    
//Thread B
    
public
 
void
 read(){
        
if
(flag){                                   
//3
            
System
.out.println(
"---i = "
 + i);      
//4
        }
    }
}