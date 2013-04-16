set term pdf
set output "10dc.pdf"

#set logscale xy


set title "10 datacenter"
set xrange [3:1000]
#set yrange [0.0001:100000]
set xlabel "Number of cloudlets in the application"
set ylabel "Time (milliseconds)"
set key left

plot\
"ott-dist.dat" u 1:5 ti "Opt-Dist" w lp pt 4,\
"noott-dist.dat" u 1:5 ti "NoOpt-Dist" w lp pt 6,\
"noott-nodist.dat" u 1:5 ti "NoOpt-NoDist" w lp pt 10,\
"ott-nodist.dat" u 1:5 ti "Opt-NoDist" w lp pt 12
