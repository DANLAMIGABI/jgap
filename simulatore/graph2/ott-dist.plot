set term pdf
set output "ott-dist.pdf"

set logscale xy

set title "Optimization - Distribution"
set xrange [3:1000]
#set yrange [0.0001:100000]
set xlabel "Number of cloudlets in the application"
set ylabel "Time (milliseconds)"
set key left

plot\
"ott-dist.dat" u 1:3 ti "1 datacenters" w lp pt 4,\
"ott-dist.dat" u 1:5 ti "10 datacenters" w lp pt 6,\
"ott-dist.dat" u 1:7 ti "100 datacenters" w lp pt 10,\
"ott-dist.dat" u 1:9 ti "1000 datacenters" w lp pt 12
