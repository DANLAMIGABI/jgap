set term pdf
set output "noott-nodist.pdf"

set logscale xy

set title "No optimization - No distribution"
set xrange [3:1000]
#set yrange [0.0001:100000]
set xlabel "Number of cloudlets in the application"
set ylabel "Time (milliseconds)"
set key left

plot\
"noott-nodist.dat" u 1:3 ti "1 datacenters" w lp pt 4,\
"noott-nodist.dat" u 1:5 ti "10 datacenters" w lp pt 6,\
"noott-nodist.dat" u 1:7 ti "100 datacenters" w lp pt 10,\
"noott-nodist.dat" u 1:9 ti "1000 datacenters" w lp pt 12
