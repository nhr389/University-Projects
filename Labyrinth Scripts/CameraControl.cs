using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CameraControl : MonoBehaviour
{
    public Transform board;

    void Start()
    {
        
    }

    // follows the rotation of the board
    void Update()
    {
        transform.position = board.position + new Vector3(0, 12, 8);
    }
}
