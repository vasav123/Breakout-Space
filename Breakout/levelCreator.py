from pygame import *
level=open(input()+".txt",'w')
running=True
pressed=False
x,y=0,0
screen = display.set_mode((1000,600))
screen2=screen.copy()
btype="1"
while running:
    screen.blit(screen2,(0,0))
    for e in event.get():
        if e.type==QUIT:
            running =False
        if e.type==KEYDOWN:
            pressed=True
    keys=key.get_pressed()
    if keys[49]==1:
        btype="1"
    if keys[50]==1:
        btype="2"
    if keys[51]==1:
        btype="3"
    if keys[13]==1 and pressed:
        level.write(str(x)+","+str(y)+","+str(btype)+"\n")
        draw.rect(screen,colour,(x,y,80,20),0)
        screen2=screen.copy()
        pressed=False
    if keys[276]==1:
        x-=5
    if keys[275]==1:
        x+=5
    if keys[273]==1:
        y-=5
    if keys[274]==1:
        y+=5
    if btype=="1":
        colour=(255,255,255)
    if btype=="2":
        colour=(0,0,255)
    if btype=="3":
        colour=(255,0,0)
        
    draw.rect(screen,colour,(x,y,80,20),0)
    print(x,y)
    time.wait(30)
    display.flip()
level.close()
quit()
