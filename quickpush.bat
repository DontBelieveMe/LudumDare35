call git add --all
call git commit -m "%time%: (%date%)"
echo %time%: "(%date%)"
call git push
