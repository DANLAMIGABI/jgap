set term pdf
set output "sim.pdf"

set logscale xy

set xrange [3:1000]
set xlabel "Number of cloudlets in the application"
set ylabel "Simulation Steps"
set key left

plot\
"data.dat" u 1:2 ti "1 datacenters" w lp pt 4,\
"data.dat" u 1:4 ti "10 datacenters" w lp pt 6,\
"data.dat" u 1:6 ti "100 datacenters" w lp pt 10,\
"data.dat" u 1:8 ti "1000 datacenters" w lp pt 12
