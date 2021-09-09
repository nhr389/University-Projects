using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BoardController : MonoBehaviour
{
    public float rotationSpeed;
    private GameManager gameManager;
    
   
   // brings in game manager to tell whether the game is active
    void Start()
    {
        gameManager = GameObject.Find("Game Manager").GetComponent<GameManager>();
    }

    // Gets directional movement and rotates the board
    void Update()
    {
        if (gameManager.gameActive)
        {
            float horizontalInput = Input.GetAxis("Horizontal");
            float verticalInput = Input.GetAxis("Vertical");
            transform.Rotate(-Vector3.forward, horizontalInput * rotationSpeed * Time.deltaTime);
            transform.Rotate(Vector3.right, verticalInput * rotationSpeed * Time.deltaTime);
        }
    
    }
}
