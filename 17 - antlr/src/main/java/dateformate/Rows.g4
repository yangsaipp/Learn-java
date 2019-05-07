grammar Rows;

// 最终采用正则在来实现，因为无法定义NOT_KEY的词法规则，比如YYYYSMMM，其中SM的词法规则无法定义，可列举的场景太多，而词法规则无法定义“非YYYY或MM或DD”类似的规则，只能针对一个字符进行排除
df: item* EOF;

item: KEY
	; 

KEY  :  'YYYY'
	|'MM'
	|'DD'
	;   
//NOT_KEY   :  [a-zA-Z]+;


