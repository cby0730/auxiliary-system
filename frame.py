import numpy as np
import sys
import os
import cv2
import obj_detec as obj
import predict2 as pred

def video_to_frame( file_path, gap ) :

    cap = cv2.VideoCapture( file_path )

    while True :
        ret, frame = cap.read()

        if ret == False:
            cap.release()
            break

        if idx == 0:
            cv2.imwrite(f"image.png", frame)
        else:
            if idx % gap == 0:
                cv2.imwrite(f"image.png", frame)

        idx += 1

def main(file_path) :

    if file_path.endswith( '.mp4' ) :
        print( 'Processing ' + file_path + '...' )
        num = video_to_frame( file_path, 100 )
        obj.main( num )

main()