set term pdf
set termoption enhanced
set termoption font "Times-Roman, 7"

set output "10dc.pdf"

set logscale xy


#set title "10 datacenter"
set xrange [10:1000]

set xlabel "Number of VMs per application"
set ylabel "Time (milliseconds)"
set key left
set key box

plot\
"noott-dist.dat" u 1:3 ti "plain, Normal"  with lp pt 4 lc rgb "black" ps 0.7,\
"ott-dist.dat" u 1:3 ti "graph-aware, Normal" with lp pt 6 lc rgb "black" ps 0.7,\
"noott-nodist.dat" u 1:3 ti "plain, Uniform"  with lp pt 8 lc rgb "black" ps 0.7,\
"ott-nodist.dat" u 1:3 ti "graph-aware, Uniform"  with lp pt 12 lc rgb "black" ps 0.7
